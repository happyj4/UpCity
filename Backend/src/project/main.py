
from fastapi import FastAPI
from .api import utility_company, image, application, authentication


app = FastAPI()

app.include_router(utility_company.router)
app.include_router(image.router)
app.include_router(application.router)
app.include_router(authentication.router)

