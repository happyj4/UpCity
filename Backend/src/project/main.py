from fastapi import FastAPI
from fastapi.staticfiles import StaticFiles

from project.api import utility_company, user, authentication, image, application


app = FastAPI()

app.mount("/images", StaticFiles(directory="/var/www/myapp/uploads/images"), name="images")

app.include_router(utility_company.router)
app.include_router(user.router)
app.include_router(authentication.router)
app.include_router(image.router)
app.include_router(application.router)




