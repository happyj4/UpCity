from sqlalchemy.orm import Session
from ..db import models
from ..schemas import utility_company_schemas

def all(db:Session):
  companies = db.query(models.UtilityCompany).all()
  return companies

def create(db:Session, request:utility_company_schemas.UtilityCompanyAdd):
    new_company = models.UtilityCompany(name = request.name, city = request.city ,address = request.address, phone = request.phone, email = request.email)
    db.add(new_company)
    db.commit()
    db.refresh(new_company)
    return new_company