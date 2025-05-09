from fastapi import APIRouter, Depends, status
from ..db import database
from sqlalchemy.orm import Session
from ..schemas import user_schemas
from ..repository import user_rep
from ..db import models
from ..hashing import Hash
from typing import List

get_db = database.get_db

router = APIRouter(tags=['Користувач🧔‍♂️'], prefix='/user')

@router.post("/", status_code=status.HTTP_201_CREATED)
def register(request: user_schemas.UserRegister, db:Session = Depends(get_db)):
  return user_rep.register(request, db)


@router.get("/ ",response_model= List[user_schemas.UserShowAll],status_code=status.HTTP_200_OK)
def show_all(db:Session = Depends(get_db)):
  return user_rep.show_all(db)

@router.post("/block", status_code=status.HTTP_201_CREATED)
def block_user(request: user_schemas.BlockUser, db: Session = Depends(get_db)):
    return user_rep.block_user(request, db)
