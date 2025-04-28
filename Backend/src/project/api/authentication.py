from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from ..db import database
from datetime import datetime, timedelta, timezone
from fastapi.security import OAuth2PasswordBearer, OAuth2PasswordRequestForm
from ..repository import authentication_rep
from ..schemas import utility_company_schemas
get_db = database.get_db

router = APIRouter(tags=['Авторизація для | Користувача | Адміна | Кп | 🔓'], prefix="/login")


@router.post("/")
def login(request:utility_company_schemas.LoginAdminCompany, db:Session = Depends(get_db)):
    return authentication_rep.login(db,request)