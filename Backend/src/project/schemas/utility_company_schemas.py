from typing import List
from pydantic import BaseModel, EmailStr


class UtilityCompany(BaseModel):
  ut_company_id: int
  name:str
  address:str
  phone:str
  email: EmailStr
  password: str
  rating: int
  


class ShowUtilityCompany(BaseModel):
  name:str
  address:str
  rating: int

  class Config():
      orm_mode = True