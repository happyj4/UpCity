from typing import Annotated, Literal

from fastapi import APIRouter, Depends, status, Query, Path
from sqlalchemy.orm import Session

from project.db.database import get_db
from project.repository import utility_company_rep
from project.oauth2 import get_current_user
from project.schemas.utility_company_schemas import (
    ShowOneUtilityCompany,
    ShowUtilityCompany,
    UtilityCompanyUpdate,
    UtilityCompanyAdd,
)


router = APIRouter(
    tags=['Комунальні підприємства 🧑‍🏭'],
    prefix="/utility_company"
)

@router.get(
    "/",
    response_model=list[ShowUtilityCompany], 
    status_code=status.HTTP_200_OK
)
def get_all_companies(
    db: Session = Depends(get_db),
    sort_by_rating: Literal["За зростанням", "За спаданням"] | None = Query(
        None, 
        description="Фільтрація за рейтингом"
    ), 
    current_user: dict = Depends(get_current_user)
):
    return utility_company_rep.get_all(current_user=current_user, db=db, sort_by_rating=sort_by_rating)


@router.get(
    "/{id}/", 
    response_model=ShowOneUtilityCompany, 
    status_code=status.HTTP_200_OK
)
def get_one_company_by_id(
    id: Annotated[int, Path(ge=1, lt=10_000)],
    db: Session = Depends(get_db),
    current_user: dict = Depends(get_current_user)
):
    return utility_company_rep.get_one(current_user=current_user, id=id, db=db)


@router.post("/", status_code=status.HTTP_201_CREATED)
def create_a_new_company(
    request: UtilityCompanyAdd,
    db: Session = Depends(get_db),
    current_user: dict = Depends(get_current_user)
):
    return utility_company_rep.create_company(db=db, request=request, current_user=current_user)


@router.put("/{id}/", status_code=status.HTTP_200_OK)
def update_company(
    id: Annotated[int, Path(ge=1, lt=10_000)],
    request: UtilityCompanyUpdate,
    db: Session = Depends(get_db),
    current_user: dict = Depends(get_current_user)
):
    return utility_company_rep.update(id=id, request=request, db=db, current_user=current_user)


@router.delete("/{id}/", status_code=status.HTTP_204_NO_CONTENT)
def destroy_company(
    id: Annotated[int, Path(ge=1, lt=10_000)],
    db: Session = Depends(get_db),
    current_user: dict = Depends(get_current_user)
):
    return utility_company_rep.destroy(id=id, db=db, current_user=current_user)
