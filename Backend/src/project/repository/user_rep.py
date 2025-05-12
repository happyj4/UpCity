from typing import Literal
from fastapi import HTTPException, status
from sqlalchemy.orm import Session
from sqlalchemy import asc, desc
from ..db import models
from ..schemas import user_schemas
from ..hashing import Hash



def register(request: user_schemas.UserRegister, db: Session):
    existing_user = db.query(models.User).filter(models.User.email == request.email).first()
    
    if existing_user:
        if existing_user.blocking:
            raise HTTPException(status_code=400, detail="Користувач уже заблокований")
        raise HTTPException(
            status_code=status.HTTP_409_CONFLICT,
            detail=f"Користувач з email = {request.email} вже існує"
        )

    new_user = models.User(
        email=request.email,
        name=request.name,
        surname=request.surname,
        password=Hash.bcrypt(request.password)
    )
    db.add(new_user)
    db.commit()
    db.refresh(new_user)
    return {
        "message": "Успішна реєстрація",
        "user": {
            "name": request.name,
            "surname": request.surname,
        }
    }


def block_user(request: user_schemas.BlockUser, db: Session, current_user:dict):
    if current_user["role"] != "admin":
        raise HTTPException(status_code=403, detail="Недостатньо прав")
    user = db.query(models.User).filter(models.User.user_id == request.user_id).first()
    if not user:
        raise HTTPException(status_code=404, detail="Користувача не знайдено")

    if user.blocking:
        raise HTTPException(status_code=400, detail="Користувач уже заблокований")

    block = models.Blocking(
        user_id=request.user_id,
        reason=request.reason
    )
    db.add(block)
    db.commit()
    db.refresh(block)
    return {"message": "Користувача заблоковано"}

def get_users(
    db: Session,
    sort_by_subscription: Literal["З підписокою", "Без підписки", "Просрочено"] | None,
    sort_by_rating: Literal["За зростанням", "За спаданням"] | None,
    sort_by_name: Literal["А-Я", "Я-А"] | None,
    current_user:dict
):
    if current_user["role"] != "admin":
        raise HTTPException(status_code=403, detail="Недостатньо прав")
    query = db.query(models.User)
    
    if sort_by_subscription == "З підписокою":
        query = query.join(models.User.subscription).filter(models.Subscription.status == "Активна")
    elif sort_by_subscription == "Без підписки":
        query = query.filter(models.User.subscription_id == None)
    elif sort_by_subscription == "Просрочено":
        query = query.join(models.User.subscription).filter(models.Subscription.status == "Неактивна")

    
    if sort_by_rating == "За зростанням":
        query = query.order_by(asc(models.User.rating))
    elif sort_by_rating == "За спаданням":
        query = query.order_by(desc(models.User.rating))
    
    if sort_by_name == "А-Я":
        query = query.order_by(asc(models.User.name))
        
    elif sort_by_name == "Я-А":
        query = query.order_by(desc(models.User.name))
        
    users = query.all()
    return users



