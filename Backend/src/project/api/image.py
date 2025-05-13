from fastapi import APIRouter, status
from fastapi import  UploadFile, File

from project.repository import image_rep


router = APIRouter(tags=['Зображення 🖼️'], prefix="/image")


@router.post("/upload/" , status_code=status.HTTP_200_OK)
async def upload_image(file: UploadFile = File(...)):
    return image_rep.upload(file)