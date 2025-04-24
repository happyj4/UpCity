from typing import List
from pydantic import BaseModel, EmailStr

class User(BaseModel):
  user_id: int
  name:str
  surname:str
  email:EmailStr
  password:str
  subscription_id: int
  image_id: int
  
