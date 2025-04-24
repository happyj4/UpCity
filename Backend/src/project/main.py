# src/project/main.py

from fastapi import FastAPI, Depends, Response, status
from contextlib import asynccontextmanager
from sqlalchemy.orm import Session
from .db import database, models

import os
import shutil
import uuid
from datetime import datetime
from fastapi import FastAPI, UploadFile, File, HTTPException
from fastapi.responses import JSONResponse
from sqlalchemy.orm import Session
from .db import models





app = FastAPI()

UPLOAD_DIR = "/var/www/myapp/uploads/images"
BASE_URL = "http://localhost:8000/images" # можно заменить на свой домен

@app.get("/")
def root():
    return {"message": "Hello"}

@app.get("/user")
def all(response:Response, db: Session = Depends(database.get_db)):
    users = db.query(models.User).all()
    if not users:
        response.status_code  = status.HTTP_404_NOT_FOUND
    return users


@app.post("/upload/")
async def upload_image(file: UploadFile = File(...)):
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