
from pydantic import BaseModel, EmailStr
from .image_schemas import ImageOut
from .subscription_schemas import SubscriptionOut
from typing import Optional


class UserRegister(BaseModel):
  email:EmailStr
  name:str
  surname:str
  password:str

class UserShowAll(BaseModel):
  user_id: int
  name:str
  surname:str
  email:EmailStr
  rating: Optional[float] = 0
  image: Optional[ImageOut] = None
  subscription: Optional[SubscriptionOut] = None

  class Config():
      orm_mode = True


class UserLogin(BaseModel):
  email:EmailStr
  password:str
  
class BlockUser(BaseModel):
  user_id: int
  reason: str

