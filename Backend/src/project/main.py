# src/project/main.py

from fastapi import FastAPI
from contextlib import asynccontextmanager
from project.crud import get_admins


@asynccontextmanager
async def lifespan(app: FastAPI):
    print("Приложение стартует. Получаем админов...")
    await get_admins()
    yield
    print("Приложение завершается.")


app = FastAPI(lifespan=lifespan)

@app.get("/")
def root():
    return {"message": "Hello"}
