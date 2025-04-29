from pydantic import BaseModel

class ImageOut(BaseModel):
    image_id: int
    image_url: str

    class Config:
        orm_mode = True