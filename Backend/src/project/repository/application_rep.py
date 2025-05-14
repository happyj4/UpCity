import requests
from fastapi.responses import JSONResponse
from fastapi import HTTPException , UploadFile
from sqlalchemy.orm import Session, joinedload

from project.db.models import Application, UtilityCompany, Report
from project.repository.image_rep import upload


def all(db:Session):
    applications = db.query(Application).all()
    return applications

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


def create(name:str, address:str, description:str, company_name:str, photo: UploadFile, db:Session, current_user:dict):
    if current_user["role"] != "user":
        raise HTTPException(status_code=403, detail="Недостатньо прав")
    
    
    user_id = current_user.get("sub")
    if not user_id:
        raise HTTPException(status_code=401, detail="Не вдалося витягти ідентифікатор користувача")

    
    company = db.query(UtilityCompany).filter(UtilityCompany.name == company_name).first()
    if not company:
        raise HTTPException(status_code=404, detail="Компанія не знайдена")

   
    lat, lon = geocode_address(address)

    
    upload_data = upload(photo)
    image_id = upload_data["image_id"]

   
    report = Report(image_id=image_id)
    db.add(report)
    db.flush()

    
    max_number = db.query(Application.application_number).order_by(Application.application_number.desc()).first()
    new_number = (max_number[0] + 1) if max_number else 1

    
    application = Application(
        name=name,
        address=address,
        description=description,
        longitude=lon,
        latitude=lat,
        status="В роботі",
        application_number=new_number,
        user_id=user_id,
        ut_company_id=company.ut_company_id,
        img_id=image_id,
        report_id=report.report_id
    )

    db.add(application)
    db.commit()
    db.refresh(application)

    return {
        "message": "Заявку успішно створено",
        "application_id": application.application_id
    }


def application_review(app_id:int, db:Session, current_user:dict):
    if current_user["role"] != "company" and current_user["role"] != "user":
        raise HTTPException(status_code=403, detail="Недостатньо прав")
    
    application = db.query(Application).options(
        joinedload(Application.image),
        joinedload(Application.utility_company)
    ).filter(Application.application_id == app_id).first()

    if not application:
        raise HTTPException(status_code=404, detail="Заявку не знайдено")

    return application

def all_by_user(db: Session, current_user:dict):
    if current_user["role"] != "user":
        raise HTTPException(status_code=403, detail="Недостатньо прав")
    
    user_id = current_user.get("sub")
    if not user_id:
        raise HTTPException(status_code=401, detail="Не вдалося витягти ідентифікатор користувача")

    applications = db.query(Application).filter(Application.user_id == user_id).all()
    return applications