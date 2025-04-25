from fastapi import APIRouter
from fastapi import  UploadFile, File
from ..repository import image_rep

router = APIRouter(tags=['Зображення'], prefix="/image")

@router.post("/upload/")
async def upload_image(file: UploadFile = File(...)):
    return image_rep.upload(file)