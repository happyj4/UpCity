from pydantic import BaseModel
from datetime import datetime
from .utility_company_schemas import ShowUtilityCompanyID

class ShowApp(BaseModel):

  name:str
  status:str
  application_date: datetime
  application_number: int
  utility_company: ShowUtilityCompanyID

  class Config():
      orm_mode = True





