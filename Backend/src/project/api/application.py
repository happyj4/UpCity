from typing import Annotated, Literal, Optional
from datetime import datetime

from fastapi import APIRouter, Depends, status, Form, File, UploadFile, Query, HTTPException
from sqlalchemy.orm import Session
from sqlalchemy import func

from project.db.database import get_db
from project.db.models import Application, UtilityCompany, Report,User, Image
from project.schemas.application_schemas import ShowApp, ApplicationReview
from project.repository import application_rep
from project.oauth2 import get_current_user
from project.repository.image_rep import upload



router = APIRouter(tags=['Заявки ⚠️'], prefix="/application")


@router.get("/", response_model=list[ShowApp], status_code=status.HTTP_200_OK)
def all(
        db: Session = Depends(get_db),
        sort_by_name: Literal["А-Я", "Я-А"] | None = Query(None, description="Фільрація за алфавітом по імені"),
        sort_by_date: Literal["За зростанням", "За спаданням"] | None = Query(None, description="Фільтрація за датою"),
        sort_by_status: Literal["В роботі", "Виконано", "Відхилено"] | None = Query(None, description="Фільтрація за статусом")
        ):
        return application_rep.all(
                                    db= db, 
                                    sort_by_name = sort_by_name, 
                                    sort_by_date = sort_by_date,
                                    sort_by_status = sort_by_status
                                    )


@router.get("/all_by_user/",response_model=list[ShowApp], status_code=status.HTTP_200_OK)
def all_by_user(
    db: Session = Depends(get_db),
    current_user: dict = Depends(get_current_user)
):
    return application_rep.all_by_user(db = db, current_user = current_user)


@router.get("/{app_id}/", status_code=status.HTTP_200_OK, response_model=ApplicationReview)
def application_review(app_id: int, db: Session = Depends(get_db), current_user: dict = Depends(get_current_user)):
    return application_rep.application_review(app_id = app_id, db = db, current_user = current_user)


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
    return application_rep.create(
        name = name, 
        address = address, 
        description = description, 
        company_name = company_name, 
        photo = photo, 
        db = db, 
        current_user = current_user
)



@router.put("/{app_id}/complete", status_code=200)
async def complete_application(
    app_id: int,
    rating: Optional[int] = Form(None),
    status: str = Form(...),
    image: UploadFile = File(...),
    db: Session = Depends(get_db),
    current_user: dict = Depends(get_current_user)
):
    if current_user["role"] != "company":
        raise HTTPException(status_code=403, detail="Тільки компанія може завершити заявку")

    application = db.query(Application).filter(Application.application_id == app_id).first()
    if not application:
        raise HTTPException(status_code=404, detail="Заявку не знайдено")

    if status not in ["Виконано", "Відхилено"]:
        raise HTTPException(status_code=400, detail="Некоректний статус")

    # Завантажуємо зображення
    uploaded_image = upload(image)
    image_id = uploaded_image["image_id"]

    if application.report_id:
        # Оновлюємо існуючий звіт
        report = db.query(Report).filter(Report.report_id == application.report_id).first()
        if not report:
            raise HTTPException(status_code=500, detail="Не знайдено звіт для заявки")

        report.image_id = image_id
        report.execution_date = datetime.utcnow()
        db.commit()
        db.refresh(report)
    else:
        # Створюємо новий звіт
        report = Report(
            image_id=image_id,
            execution_date=datetime.utcnow()
        )
        db.add(report)
        db.flush()  # Щоб отримати report_id

        application.report_id = report.report_id
        db.commit()
        db.refresh(report)

    # Оновлюємо статус заявки
    application.status = status
    if rating:
        application.user_rating = rating
    db.commit()
    db.refresh(application)

    # Перерахунок рейтингу користувача
    if rating:
        user = db.query(User).filter(User.user_id == application.user_id).first()
        if user:
            total_ratings = db.query(func.sum(Application.user_rating)).filter(
                Application.user_id == user.user_id,
                Application.user_rating.isnot(None)
            ).scalar() or 0

            rating_count = db.query(func.count(Application.application_id)).filter(
                Application.user_id == user.user_id,
                Application.user_rating.isnot(None)
            ).scalar() or 1

            user.rating = round(total_ratings / rating_count, 2)
            db.commit()

    # Перерахунок рейтингу компанії
    company_id = application.ut_company_id
    if company_id:
        total_applications = db.query(func.count(Application.application_id)).filter(
            Application.ut_company_id == company_id
        ).scalar() or 1

        completed_applications = db.query(func.count(Application.application_id)).filter(
            Application.ut_company_id == company_id,
            Application.status == "Виконано"
        ).scalar() or 0

        company = db.query(UtilityCompany).filter(UtilityCompany.ut_company_id == company_id).first()
        if company:
            company.rating = round((completed_applications / total_applications) * 100)
            db.commit()

    # Формируем данные для возврата
    # Получаем URL основного изображения заявки
    main_image = None
    if application.img_id:
        img_obj = db.query(Image).filter(Image.image_id == application.img_id).first()
        if img_obj:
            main_image = {"image_url": img_obj.image_url}

    # Получаем отчет с изображением
    report_out = None
    if report and report.image_id:
        report_img_obj = db.query(Image).filter(Image.image_id == report.image_id).first()
        report_out = {
            "report_id": report.report_id,
            "execution_date": report.execution_date,
            "image": {"image_url": report_img_obj.image_url} if report_img_obj else None
        }

    return {
        "message": "Заявку оновлено успішно",
        "application": {
            "application_id": application.application_id,
            "status": application.status,
            "image": main_image,
            "report": report_out,
        }
    }



