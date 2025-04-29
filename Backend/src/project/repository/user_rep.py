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
    return {"message": "Успішна регістрація"}

def show_all(db:Session):
    users = db.query(models.User).all()
    return users