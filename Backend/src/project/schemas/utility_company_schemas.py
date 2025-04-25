from pydantic import BaseModel, EmailStr
from typing import Optional

class UtilityCompanyAdd(BaseModel):
  name:str
  city:str
  address:str
  phone:str
  email: EmailStr
  
  


class ShowUtilityCompany(BaseModel):
  name:str
  address:str
  rating: Optional[int]

  class Config():
      orm_mode = True