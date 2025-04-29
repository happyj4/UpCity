from fastapi import APIRouter, Depends, status
from ..db import database
from sqlalchemy.orm import Session
from ..schemas import utility_company_schemas
from ..repository import utility_company_rep



get_db = database.get_db

router = APIRouter(tags=['Комунальні підприємтсва 🧑‍🏭'], prefix="/utility_company")

@router.get("/{id}", response_model=utility_company_schemas.ShowOneUtilityCompany, status_code=status.HTTP_200_OK)
def get_one(id:int, db:Session = Depends(get_db)):
    return utility_company_rep.get_one(id, db)

@router.get("/", response_model=list[utility_company_schemas.ShowUtilityCompany], status_code=status.HTTP_200_OK)
def all(db:Session = Depends(get_db)):
    return utility_company_rep.all(db)


@router.post("/",status_code=status.HTTP_201_CREATED)
def create(request: utility_company_schemas.UtilityCompanyAdd ,db:Session = Depends(get_db)):
    return utility_company_rep.create(db, request)


@router.put("/{id}", status_code=status.HTTP_200_OK)
def update(id:int , request: utility_company_schemas.UtilityCompanyUpdate ,db:Session = Depends(get_db)):
    return utility_company_rep.update(id, request , db)