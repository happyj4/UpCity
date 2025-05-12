from fastapi import APIRouter, Depends, status, HTTPException
from ..db import database, models
from sqlalchemy.orm import Session
from ..schemas import application_schemas
from ..repository import application_rep, image_rep
from fastapi import Form, File, UploadFile
import requests
from ..oauth2 import get_current_user
from fastapi.responses import JSONResponse





get_db = database.get_db

router = APIRouter(tags=['Заявки ⚠️'], prefix="/application")

@router.get("/", response_model=list[application_schemas.ShowApp], status_code=status.HTTP_200_OK,)
def all(db: Session = Depends(get_db)):
    return application_rep.all(db)


# Геокодинг через OpenStreetMap

def geocode_address(address: str):
    url = "https://nominatim.openstreetmap.org/search"
    params = {"q": address, "format": "json"}
    headers = {"User-Agent": "UpCityApp/1.0 (contact@example.com)"}

    try:
        response = requests.get(url, params=params, headers=headers, timeout=10)
    except requests.RequestException as e:
        raise HTTPException(status_code=502, detail=f"Geocoding request failed: {str(e)}")

    if response.status_code != 200:
        raise HTTPException(status_code=502, detail=f"Geocoding error: {response.status_code} - {response.text}")

    try:
        data = response.json()
    except Exception as e:
        raise HTTPException(status_code=502, detail=f"Geocoding JSON error: {str(e)}. Response was: {response.text}")

    if data:
        return float(data[0]["lat"]), float(data[0]["lon"])

    return None, None
@router.post("/create/")
async def create(
    name: str = Form(...),
    address: str = Form(...),
    description: str = Form(...),
    company_name: str = Form(...),
    photo: UploadFile = File(...),
    db: Session = Depends(get_db),
    current_user: dict = Depends(get_current_user),
):
    # Проверка, что текущий пользователь имеет роль "user"
    if current_user["role"] != "user":
        raise HTTPException(status_code=403, detail="Недостатньо прав")
    
    # Проверка на наличие sub в current_user
    user_id = current_user.get("sub")
    if not user_id:
        raise HTTPException(status_code=401, detail="Неудалось извлечь идентификатор пользователя")

    # Поиск компании по имени
    company = db.query(models.UtilityCompany).filter(models.UtilityCompany.name == company_name).first()
    if not company:
        raise HTTPException(status_code=404, detail="Компанія не знайдена")

    # Геокодинг
    lat, lon = geocode_address(address)

    # 📸 Загрузка фото через твою функцию
    upload_response: JSONResponse = image_rep.upload(photo)
    upload_data = upload_response.body.decode()  # JSONResponse возвращает байты
    import json
    upload_data = json.loads(upload_data)
    image_id = upload_data["image_id"]

    # 📄 Создание пустого отчета
    report = models.Report(image_id=image_id)
    db.add(report)
    db.flush()

    # 📄 Генерация номера заявки
    max_number = db.query(models.Application.application_number).order_by(models.Application.application_number.desc()).first()
    new_number = (max_number[0] + 1) if max_number else 1

    # ✅ Создание заявки
    application = models.Application(
        name=name,
        address=address,
        description=description,
        longitude=lon,
        latitude=lat,
        status="В роботі",
        application_number=new_number,
        user_id=user_id,  # теперь user_id точно строка
        ut_company_id=company.ut_company_id,
        img_id=image_id,
        report_id=report.report_id
    )

    db.add(application)
    db.commit()
    db.refresh(application)

    return {
        "message": "Заявка успешно создана",
        "application_id": application.application_id
    }
