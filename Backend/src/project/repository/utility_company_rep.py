from sqlalchemy.orm import Session
from ..db import models

def all(db:Session):
  companies = db.query(models.UtilityCompany).all()
  return companies