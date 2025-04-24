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
  

















    #   user_id = Column(Integer, primary_key=True, index=True)
    # name = Column(String(100))
    # surname = Column(String(100))
    # email = Column(String(255), unique=True, nullable=False)
    # password = Column(String(255))
    # subscription_id = Column(Integer, ForeignKey("app_subscription.subscription_id", ondelete="SET NULL"))
    # image_id = Column(Integer, ForeignKey("image.image_id", ondelete="SET NULL"))

    # subscription = relationship("Subscription", back_populates="users")
    # blocking = relationship("Blocking", back_populates="user", uselist=False)
    # image = relationship("Image", back_populates="users")
    # applications = relationship("Application", back_populates="user")