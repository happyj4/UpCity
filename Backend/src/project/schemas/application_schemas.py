from pydantic import BaseModel
from datetime import datetime
from .utility_company_schemas import ShowUtilityCompanyID
from .image_schemas import ImageOut

class ShowApp(BaseModel):
  application_id:int
  name:str
  status:str
  application_date: datetime
  application_number: int
  utility_company: ShowUtilityCompanyID

  class Config():
      orm_mode = True

class application_review(BaseModel):
    application_id: int
    application_number: int
    name: str
    address: str
    utility_company: ShowUtilityCompanyID
    description: str
    status: str
    application_date: datetime
    longitude: float
    latitude: float
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





