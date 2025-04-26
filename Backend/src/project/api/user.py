from fastapi import APIRouter, Depends, status
from ..db import database
from sqlalchemy.orm import Session
from ..schemas import user_schemas
from ..repository import user_rep
from ..db import models
from ..hashing import Hash

get_db = database.get_db

router = APIRouter(tags=['Користувач🧔‍♂️'], prefix='/user')

@router.post("/")
def register(request: user_schemas.UserRegister, db:Session = Depends(get_db)):
  return user_rep.register(request, db)

