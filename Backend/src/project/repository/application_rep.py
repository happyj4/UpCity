from sqlalchemy.orm import Session
from ..db import models


def all(db:Session):
    applications = db.query(models.Application).all()
    return applications

