from typing import Annotated, Literal

from fastapi import APIRouter, Depends, status, Form, File, UploadFile, Query
from sqlalchemy.orm import Session

from project.db.database import get_db
from project.schemas.application_schemas import ShowApp, ApplicationReview
from project.repository import application_rep
from project.oauth2 import get_current_user



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
