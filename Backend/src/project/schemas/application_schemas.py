from pydantic import BaseModel
from datetime import datetime
from typing import Optional

from project.schemas.utility_company_schemas import ShowUtilityCompanyID
from project.schemas.image_schemas import ImageOut


class ShowApp(BaseModel):
    application_id:int
    name:str
    status:str
    application_date: datetime
    application_number: int
    longitude: Optional[float] = None
    latitude: Optional[float] = None
    utility_company: ShowUtilityCompanyID

    class Config():
        orm_mode = True

class ApplicationReview(BaseModel):
    application_id: int
    application_number: int
    name: str
    address: str
    utility_company: ShowUtilityCompanyID
    description: str
    status: str
    application_date: datetime
    longitude: Optional[float] = None
    latitude: Optional[float] = None
    image: ImageOut

    class Config:
        orm_mode = True

class CreateApp(BaseModel):
    name:str
    application_date: datetime
    application_number: int
    utility_company: ShowUtilityCompanyID

    class Config():
        orm_mode = True





