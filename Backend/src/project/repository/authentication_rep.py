from fastapi import HTTPException, status
from sqlalchemy.orm import Session
from ..db import models
from ..schemas import utility_company_schemas
from ..hashing import Hash
from .. import oauth2


def login(db: Session, request: utility_company_schemas.LoginAdminCompany):
    # Адмін
    admin = db.query(models.Admin).filter(models.Admin.email == request.email).first()
    if admin:
        if admin.password != request.password:
            raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="Невірний пароль для адміна")
        
        access_token = oauth2.create_access_token(data={"sub": admin.email, "role": "admin"})
        return {
            "message": "Успішний вхід ADMIN",
            "access_token": access_token,
            "token_type": "bearer"
        }

    # Компанія
    company = db.query(models.UtilityCompany).filter(models.UtilityCompany.email == request.email).first()
    if company:
        if not Hash.verify(request.password, company.password):
            raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="Невірний пароль для КП")
        
        access_token = oauth2.create_access_token(data={"sub": company.email, "role": "company"})
        return {
            "message": "Успішний вхід COMPANY",
            "access_token": access_token,
            "token_type": "bearer"
        }

    # Користувач
    user = db.query(models.User).filter(models.User.email == request.email).first()
    if user:
        if user.blocking:
            raise HTTPException(status_code=400, detail="Користувач уже заблокований")
        if not Hash.verify(request.password, user.password):
            raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="Невірний пароль для користувача")
        
        access_token = oauth2.create_access_token(data={"sub": user.email, "role": "user"})
        return {
            "message": "Успішний вхід USER",
            "access_token": access_token,
            "token_type": "bearer",
            "user": {
                "name": user.name,
                "surname": user.surname
            }
        }

    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail=f"З email = {request.email} не зареєстровано жодний обліковий запис")
