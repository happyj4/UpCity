from fastapi import HTTPException
from datetime import datetime
import os
import shutil
import uuid
from sqlalchemy.orm import Session
from fastapi.responses import JSONResponse
from sqlalchemy.orm import Session
from fastapi import  UploadFile
from ..db import models, database
from dotenv import load_dotenv
import os

load_dotenv()

get_db = database.get_db

UPLOAD_DIR = f"{os.getenv('UPLOAD_DIR')}"
BASE_URL = f"{os.getenv('BASE_URL')}"

def upload(file:UploadFile):
    if not file.filename.lower().endswith((".jpg", ".jpeg", ".png", ".gif")):
      raise HTTPException(status_code=400, detail="Неподдерживаемый формат файла")

    filename = f"{uuid.uuid4().hex}_{file.filename}"
    filepath = os.path.join(UPLOAD_DIR, filename)

    with open(filepath, "wb") as buffer:
        shutil.copyfileobj(file.file, buffer)

    image_url = f"{BASE_URL}/{filename}"
    image = models.Image(image_url=image_url, image_date=datetime.utcnow())

    db: Session = next(database.get_db())
    db.add(image)
    db.commit()
    db.refresh(image)

    return JSONResponse(content={
        "message": "Изображение успешно загружено",
        "image_id": image.image_id,
        "image_url": image.image_url
    })




