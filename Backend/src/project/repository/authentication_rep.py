from fastapi import HTTPException
from sqlalchemy.orm import Session
from ..db import models
from ..schemas import utility_company_schemas
from ..hashing import Hash


def login(db: Session, request:utility_company_schemas.LoginAdminCompany):
    admin = db.query(models.Admin).filter(models.Admin.email == request.email).first()
    
    if admin:
        if not(admin.password == request.password):
            raise Exception("Невірний пароль для адміна")
        return "admin" 
    
    company = db.query(models.UtilityCompany).filter(models.UtilityCompany.email == request.email).first()
    
    if company:
        if not Hash.verify(company.password, request.password):
            raise Exception("Невірний пароль для КП")
        return "company" 
    
    user = db.query(models.User).filter(models.User.email == request.email).first()
    
    if user:
        if not Hash.verify(user.password, request.password):
            raise Exception("Невірний пароль для користувача")
        return "Successful"

    raise Exception("User not found")
