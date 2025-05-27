from pydantic import BaseModel, EmailStr
from typing import Optional, Annotated
from annotated_types import MaxLen, MinLen

from project.schemas.image_schemas import ImageOut
from project.schemas.subscription_schemas import SubscriptionOut

class BlockUser(BaseModel):
    user_id: int
    reason: str

class GoogleAuthRequest(BaseModel):
    id_token: str
class BlockingOut(BaseModel):
    reason:str
    
class UserRegister(BaseModel):
    email: EmailStr
    name: Annotated[str, MinLen(3), MaxLen(35)]
    surname: Annotated[str, MinLen(3), MaxLen(35)]
    password: Annotated[str, MinLen(8), MaxLen(64)]

class UserShowAll(BaseModel):
    user_id: int
    name: str
    surname: str
    email: EmailStr
    rating: Optional[float] = 0
    image: Optional[ImageOut] = None
    subscription: Optional[SubscriptionOut] = None
    blocking: Optional[BlockingOut] = None

    model_config = {
        "from_attributes": True
    }


class UserLogin(BaseModel):
    email:EmailStr
    password:str
  

class EmailRequest(BaseModel):
    to: EmailStr
    subject: str
    message: str

    

class PaymentRequest(BaseModel):
    paymentToken: str

