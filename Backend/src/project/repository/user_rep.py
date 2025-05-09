from fastapi import HTTPException, status
from sqlalchemy.orm import Session
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

def show_all(db:Session):
    users = db.query(models.User).all()
    return users

def block_user(request: user_schemas.BlockUser, db: Session):
    user = db.query(models.User).filter(models.User.user_id == request.user_id).first()
    if not user:
        raise HTTPException(status_code=404, detail="Користувача не знайдено")

    block = models.Blocking(
        user_id=request.user_id,
        reason=request.reason,
        block_date=request.block_date
    )
    db.add(block)
    db.commit()
    db.refresh(block)
    return {"message": "Користувача заблоковано"}