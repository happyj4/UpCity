from fastapi import APIRouter, Depends
from ..db import database
from sqlalchemy.orm import Session
from ..schemas import utility_company_schemas
from ..repository import utility_company_rep


get_db = database.get_db

router = APIRouter(tags=['Комунальні підприємтсва'], prefix="/utility_company")

@router.get("/", response_model=list[utility_company_schemas.ShowUtilityCompany], status_code=200)
def all(db:Session = Depends(get_db)):
    return utility_company_rep.all(db)


@router.post("/")
def create(request: utility_company_schemas.UtilityCompanyAdd ,db:Session = Depends(get_db)):
    return utility_company_rep.create(db, request)