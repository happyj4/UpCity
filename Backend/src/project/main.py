
from fastapi import FastAPI
from .api import utility_company, image 


app = FastAPI()

app.include_router(utility_company.router)
app.include_router(image.router)
