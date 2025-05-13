from fastapi import APIRouter, Depends, status
from sqlalchemy.orm import Session
from fastapi.security import  OAuth2PasswordRequestForm

from project.db.database import get_db
from project.repository import authentication_rep
from project.schemas import utility_company_schemas


router = APIRouter(tags=['Авторизація для | Користувача | Адміна | Кп | 🔓'], prefix="/login")


@router.post("/", status_code=status.HTTP_200_OK)
def login(request: OAuth2PasswordRequestForm = Depends(), db: Session = Depends(get_db)):
    # Email — это username в форме
    email = request.username
    password = request.password
    # передаём дальше как вручную собранный Pydantic объект
    login_data = utility_company_schemas.LoginAdminCompany(email=email, password=password)
    return authentication_rep.login(db, login_data)
