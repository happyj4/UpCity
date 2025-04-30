from fastapi import HTTPException, status
from sqlalchemy.orm import Session
from ..db import models
from ..schemas import utility_company_schemas
from ..hashing import Hash
from ..token import create_access_token
from fastapi.security import OAuth2PasswordRequestForm


def login(db: Session, request: OAuth2PasswordRequestForm):
    email = request.username
    password = request.password

    admin = db.query(models.Admin).filter(models.Admin.email == email).first()
    
    if admin and admin.password == password: 
        access_token = create_access_token(data={"sub": user.email, "user_id": user.user_id})
        return {"access_token": access_token, "token_type": "bearer"}

    company = db.query(models.UtilityCompany).filter(models.UtilityCompany.email == email).first()
    
    if company and Hash.verify(company.password, password):
        access_token = create_access_token(data={"sub": user.email, "user_id": user.user_id})
        return {"access_token": access_token, "token_type": "bearer"}

    user = db.query(models.User).filter(models.User.email == email).first()
    
    if user and Hash.verify(user.password, password):
        access_token = create_access_token(data={"sub": user.email, "user_id": user.user_id})
        return {
            "access_token": access_token,
            "token_type": "bearer",
            "data": {
                "name": user.name,
                "surname": user.surname
            }
        }

    raise HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="Невірний email або пароль"
    )
