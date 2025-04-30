from datetime import datetime, timedelta, timezone
from dotenv import load_dotenv
import os
from jose import JWTError, jwt
from .schemas import token_schemas



load_dotenv()

def create_access_token(data: dict):
    to_encode = data.copy()

    expire = datetime.now(timezone.utc) + timedelta(minutes= 30)
    to_encode.update({"exp": expire})
    encoded_jwt = jwt.encode(to_encode, f"{os.getenv('SECRET_KEY')}", algorithm=f"{os.getenv('ALGORITHM')}")
    return encoded_jwt


def verify_token(token:str, credentials_exception):
    try:
        payload = jwt.decode(token, os.getenv('SECRET_KEY'), algorithms=[os.getenv('ALGORITHM')])
        email:str = payload.get("sub")
        if email is None:
            raise credentials_exception
        token_data = token_schemas.TokenData(email=email)
        return token_data 
    except JWTError:
        raise credentials_exception