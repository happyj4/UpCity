from fastapi import HTTPException, status
from sqlalchemy.orm import Session
from sqlalchemy import asc, desc
from typing import Optional
from ..db import models
from ..schemas import utility_company_schemas
from ..hashing import Hash
from typing import List, Literal

def all(current_user:dict, db: Session, sort_by_rating: Literal["За зростанням", "За спаданням"] | None):
    if current_user["role"] != "admin" and current_user["role"] != "user":
        raise HTTPException(status_code=403, detail="Недостатньо прав")
    query = db.query(models.UtilityCompany)

    if sort_by_rating == "За зростанням":
        query = query.order_by(asc(models.UtilityCompany.rating))
    elif sort_by_rating == "За спаданням":
        query = query.order_by(desc(models.UtilityCompany.rating))

    return query.all()

def get_one(id, db:Session,current_user:dict):
    if current_user["role"] != "admin":
        raise HTTPException(status_code=403, detail="Недостатньо прав")
    company = db.query(models.UtilityCompany).filter(models.UtilityCompany.ut_company_id == id).first()
    if not company:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"Компанію за id = {id} не знайдено")
    return company

def create(db: Session, request: utility_company_schemas.UtilityCompanyAdd, current_user:dict):
    if current_user["role"] != "admin":
        raise HTTPException(status_code=403, detail="Недостатньо прав")
    existing_company = db.query(models.UtilityCompany).filter(models.UtilityCompany.email == request.email).first()
    if existing_company:
        raise HTTPException(status_code=status.HTTP_409_CONFLICT, detail=f"Компанія з email = {request.email} вже існує")

    new_company = models.UtilityCompany(
        name=request.name,
        address=request.address,
        phone=request.phone,
        email=request.email,
        password=Hash.bcrypt(request.password)
    )
    db.add(new_company)
    db.commit()
    db.refresh(new_company)
    return new_company


def update(id, request: utility_company_schemas.UtilityCompanyUpdate, db: Session, current_user: dict):
    if current_user["role"] != "admin":
        raise HTTPException(status_code=403, detail="Недостатньо прав")
    company = db.query(models.UtilityCompany).filter(models.UtilityCompany.ut_company_id == id).first()
    if not company:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"Компанію за id = {id} не знайдено")


    existing_company = db.query(models.UtilityCompany).filter(models.UtilityCompany.email == request.email, models.UtilityCompany.ut_company_id != id).first()
    if existing_company:
        raise HTTPException(status_code=status.HTTP_409_CONFLICT, detail=f"Компанія з email = {request.email} вже існує")

    company.name = request.name
    company.address = request.address
    company.phone = request.phone
    company.email = request.email
    db.commit()
    db.refresh(company)
    return company

def destroy(id, db:Session, current_user:dict):
    if current_user["role"] != "admin":
        raise HTTPException(status_code=403, detail="Недостатньо прав")
    company  = db.query(models.UtilityCompany).filter(models.UtilityCompany.ut_company_id == id).delete(synchronize_session=False)
    db.commit()
    if not company:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail = f"КП з id = {id} не знайдено")
    return 
