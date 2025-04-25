from pydantic import BaseModel, EmailStr
from typing import Optional

class UtilityCompanyAdd(BaseModel):
  name:str
  city:str
  address:str
  phone:str
  email: EmailStr
  
class UtilityCompanyUpdate(UtilityCompanyAdd):
  name:str
  city:str
  address:str
  phone:str
  email: EmailStr
  
  


class ShowUtilityCompany(BaseModel):
  name:str
  city:str
  address:str
  phone:str
  rating: Optional[int] = 0

  class Config():
      orm_mode = True