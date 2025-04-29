from fastapi import HTTPException, status
from sqlalchemy.orm import Session
from ..db import models
from ..schemas import utility_company_schemas
from ..hashing import Hash


def login(db: Session, request:utility_company_schemas.LoginAdminCompany):
    admin = db.query(models.Admin).filter(models.Admin.email == request.email).first()
    
    if admin:
        if not(admin.password == request.password):
            raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED,detail="Невірний пароль для адміна")
        return {"message": "Успішний вхід ADMIN"}
    
    company = db.query(models.UtilityCompany).filter(models.UtilityCompany.email == request.email).first()
    
    if company:
        if not Hash.verify(company.password, request.password):
            raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED,detail="Невірний пароль для КП")
        return {"message": "Успішний вхід COMPANY"}
    
    user = db.query(models.User).filter(models.User.email == request.email).first()
    
    if user:
        if not Hash.verify(user.password, request.password):
            raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED,detail="Невірний пароль для користувача")
        return {"message": "Успішний вхід USER"}

    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail= f"З email = {request.email} не зареєстровано жодний обліковий запис")
