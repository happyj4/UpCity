from fastapi import APIRouter, Depends, status, Query, Path
from ..db import database
from sqlalchemy.orm import Session
from ..schemas import utility_company_schemas
from ..repository import utility_company_rep
from typing import Optional, Annotated
from ..oauth2 import get_current_user
from typing import List, Literal




get_db = database.get_db

router = APIRouter(tags=['Комунальні підприємтсва 🧑‍🏭'], prefix="/utility_company")

@router.get("/{id}/", response_model=utility_company_schemas.ShowOneUtilityCompany, status_code=status.HTTP_200_OK)
def get_one(id: Annotated[int, Path(ge=1, lt=10_000)], db:Session = Depends(get_db), current_user: dict = Depends(get_current_user)):
    return utility_company_rep.get_one(current_user=current_user,id=id, db=db)

@router.get("/", response_model=list[utility_company_schemas.ShowUtilityCompany], status_code=status.HTTP_200_OK)
def all(db:Session = Depends(get_db),sort_by_rating: Literal["За зростанням", "За спаданням"] | None = Query(None, description="Фільтраця за рейтингом"), current_user: dict = Depends(get_current_user)):
    return utility_company_rep.all(current_user=current_user, db=db, sort_by_rating=sort_by_rating)


@router.post("/",status_code=status.HTTP_201_CREATED)
def create(request: utility_company_schemas.UtilityCompanyAdd ,db:Session = Depends(get_db), current_user: dict = Depends(get_current_user)):
    return utility_company_rep.create(db=db, request= request, current_user=current_user)

@router.put("/{id}/", status_code=status.HTTP_200_OK)
def update(id: Annotated[int, Path(ge=1, lt=10_000)] , request: utility_company_schemas.UtilityCompanyUpdate ,db:Session = Depends(get_db), current_user: dict = Depends(get_current_user)):
    return utility_company_rep.update(id = id, request = request , db = db, current_user=current_user)

@router.delete("/{id}/", status_code=status.HTTP_204_NO_CONTENT)
def destroy(id: Annotated[int, Path(ge=1, lt=10_000)] ,db:Session = Depends(get_db), current_user: dict = Depends(get_current_user)):
    return utility_company_rep.destroy(id = id,  db = db, current_user=current_user)