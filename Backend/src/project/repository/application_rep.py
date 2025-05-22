from datetime import date
from typing import Annotated, Literal

import requests
from fastapi.responses import JSONResponse
from fastapi import HTTPException , UploadFile
from sqlalchemy.orm import Session, joinedload
from sqlalchemy import func, asc ,desc

from project.db.models import Application, UtilityCompany, Report, User
from project.repository.image_rep import upload


def all(
        db:Session,
        sort_by_name:Literal["А-Я", "Я-А"] | None, 
        sort_by_date:Literal["За зростанням", "За спаданням"] | None,
        sort_by_status:Literal["В роботі", "Виконано", "Відхилено"] | None
        ):
        
    query = db.query(Application)  
    
    if sort_by_name == "А-Я":
        query = query.order_by(asc(Application.name))
    elif sort_by_name == "Я-А":
        query = query.order_by(desc(Application.name))
    
    if sort_by_date == "За зростанням":
        query = query.order_by(asc(Application.application_date))
    elif sort_by_date == "За спаданням":
        query = query.order_by(desc(Application.application_date))
    
    if sort_by_status:
        query = query.filter(Application.status == sort_by_status)
    
    applications = query.all()
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


def create(name: str, address: str, description: str, company_name: str, photo: UploadFile, db: Session, current_user: dict):
    if current_user["role"] != "user":
        raise HTTPException(status_code=403, detail="Недостатньо прав")
    
    user_id = current_user.get("sub")
    if not user_id:
        raise HTTPException(status_code=401, detail="Не вдалося витягти ідентифікатор користувача")

    user = db.query(User).filter(User.user_id == user_id).outerjoin(User.subscription).first()
    if not user:
        raise HTTPException(status_code=404, detail="Користувач не знайдений")

    today = date.today()
    has_active_subscription = (
        user.subscription is not None and
        user.subscription.status == "Активна" and
        user.subscription.start_date <= today <= user.subscription.end_date
    )

    applications_today = db.query(Application).filter(
        Application.user_id == user_id,
        func.date(Application.application_date) == today
    ).count()

    if not has_active_subscription and applications_today >= 3:
        raise HTTPException(status_code=429, detail="Ви досягли ліміту заявок на сьогодні. Оформіть підписку для необмеженого доступу.")

    company = db.query(UtilityCompany).filter(UtilityCompany.name == company_name).first()
    if not company:
        raise HTTPException(status_code=404, detail="Компанія не знайдена")

    lat, lon = geocode_address(address)
    upload_data = upload(photo)
    image_id = upload_data["image_id"]

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
        report_id=None  # не прив'язуємо репорт
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

def all_by_user(db: Session, current_user: dict):
    if current_user["role"] not in ["user", "company"]:
        raise HTTPException(status_code=403, detail="Недостатньо прав")
    
    user_id = current_user.get("sub")

    if not user_id:
        raise HTTPException(status_code=401, detail="Не вдалося витягти ідентифікатор користувача")

    if current_user["role"] == "company":
        applications = db.query(Application).filter(Application.ut_company_id == user_id).order_by(desc(Application.application_date))
    else:  # role == "user"
        applications = db.query(Application).filter(Application.user_id == user_id).order_by(desc(Application.application_date))

    return applications