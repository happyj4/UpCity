from fastapi import APIRouter, Depends, status
from ..db import database
from sqlalchemy.orm import Session
from ..schemas import application_schemas
from ..repository import application_rep



get_db = database.get_db

router = APIRouter(tags=['Заявки ⚠️'], prefix="/application")

@router.get("/", response_model=list[application_schemas.ShowApp])
def all(db: Session = Depends(get_db)):
    return application_rep.all(db)
