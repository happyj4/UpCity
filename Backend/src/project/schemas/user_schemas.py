from pydantic import BaseModel, EmailStr

class UserRegister(BaseModel):
  email:EmailStr
  name:str
  surname:str
  password:str


class UserLogin(BaseModel):
  email:EmailStr
  password:str
