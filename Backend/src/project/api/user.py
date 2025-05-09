from typing import List, Literal
from fastapi import APIRouter, Depends, status, Query
from ..db import database
from sqlalchemy.orm import Session
from ..schemas import user_schemas
from ..repository import user_rep
from ..db import models
from ..hashing import Hash

get_db = database.get_db

router = APIRouter(tags=['Користувач🧔‍♂️'], prefix='/user')

@router.post("/", status_code=status.HTTP_201_CREATED)
def register(request: user_schemas.UserRegister, db:Session = Depends(get_db)):
    return user_rep.register(request, db)


@router.get("/",response_model= List[user_schemas.UserShowAll],status_code=status.HTTP_200_OK)
def get_users(
    db: Session = Depends(get_db),
    has_subscription: bool | None = Query(None, description="Filter users by subscription status"),
    sort_by: Literal["name", "surname", "rating"] | None = Query(None, description="Field to sort by"),
    sort_order: Literal["asc", "desc"] | None = Query(None, description="Sort order (ascending or descending)"),
):
    """
    Get users with filtering and sorting options:
    - Filter by subscription status (has subscription or not)
    - Sort by name, surname, or rating in ascending or descending order
    """
    return user_rep.get_users(db, has_subscription=has_subscription, sort_by=sort_by, sort_order=sort_order)

