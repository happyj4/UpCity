from pydantic import BaseModel, EmailStr
from typing import Optional

class UtilityCompanyAdd(BaseModel):
  name:str
  address:str
  phone:str
  email: EmailStr
  password:str
  
class UtilityCompanyUpdate(BaseModel):
  name:str
  address:str
  phone:str
  email: EmailStr
  
  
class ShowUtilityCompany(BaseModel):
  name:str
  address:str
  phone:str
  rating: Optional[int] = 0

  class Config():
      orm_mode = True

class ShowUtilityCompanyID(BaseModel):
  name:str

  class Config():
      orm_mode = True

class LoginAdminCompany(BaseModel):
  email: EmailStr
  password: str