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

def get_users(
    db: Session,
    has_subscription: bool | None,
    sort_by: Literal["name", "surname", "rating"] | None,
    sort_order: Literal["asc", "desc"] | None,
):
    """
    Get users with filtering and sorting options:
    - Filter by subscription status (has subscription or not)
    - Sort by name, surname, or rating in ascending or descending order
    """
    query = db.query(models.User)
    
    if has_subscription is not None:
        if has_subscription:
            query = query.filter(models.User.subscription_id != None)
        else:
            query = query.filter(models.User.subscription_id == None)
    
    if sort_by:
        sort_column = getattr(models.User, sort_by)
        
        if sort_order == "desc":
            query = query.order_by(desc(sort_column))
        else:
            query = query.order_by(asc(sort_column))
    
    users = query.all()
    return users