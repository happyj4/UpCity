from pydantic import BaseModel
from datetime import date

class SubscriptionOut(BaseModel):
    subscription_id: int
    status: str
    start_date: date
    end_date: date

    class Config:
        orm_mode = True