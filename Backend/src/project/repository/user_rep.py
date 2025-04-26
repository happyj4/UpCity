from fastapi import HTTPException
from sqlalchemy.orm import Session
from ..db import models
from ..schemas import user_schemas
from ..hashing import Hash



def register(request: user_schemas.UserRegister, db:Session):
    new_user = models.User(email = request.email, name = request.name, surname = request.surname, password = Hash.bcrypt(request.password))
    db.add(new_user)
    db.commit()
    db.refresh(new_user)
    return "Successful"
