from fastapi import APIRouter, Depends, status, HTTPException
from ..db import database, models
from sqlalchemy.orm import Session
from ..schemas import application_schemas
from ..repository import application_rep, image_rep
from fastapi import Form, File, UploadFile

from ..oauth2 import get_current_user





get_db = database.get_db

router = APIRouter(tags=['Заявки ⚠️'], prefix="/application")

@router.get("/", response_model=list[application_schemas.ShowApp], status_code=status.HTTP_200_OK)
def all(db: Session = Depends(get_db)):
    return application_rep.all(db)

@router.get("/all_by_user/",response_model=list[application_schemas.ShowApp], status_code=status.HTTP_200_OK)
def all_by_user(
    db: Session = Depends(get_db),
    current_user: dict = Depends(get_current_user)
):
    return application_rep.all_by_user(db = db, current_user = current_user)



@router.get("/{app_id}/", status_code=status.HTTP_200_OK, response_model=application_schemas.application_review)
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
    return application_rep.create(name = name, address = address, description = description, company_name = company_name, photo = photo, db = db, current_user = current_user)
