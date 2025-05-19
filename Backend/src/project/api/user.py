from typing import List, Literal, Annotated
from datetime import date, timedelta

from fastapi import APIRouter, Depends, status, Query, Path, UploadFile, Form, File, Request, HTTPException
from sqlalchemy.orm import Session
from annotated_types import MinLen, MaxLen
from pydantic import EmailStr
from google.oauth2 import id_token
from google.auth.transport import requests as google_requests
from dotenv import load_dotenv
import os

from project.db.database import get_db
from project.db.models import Subscription, User
from project.schemas.user_schemas import UserRegister , UserShowAll , BlockUser, PaymentRequest, GoogleAuthRequest
from project.repository import user_rep
from project.oauth2 import get_current_user
from project.jwt_handler import create_access_token
from project.hashing import Hash

load_dotenv()

router = APIRouter(tags=['Користувач🧔‍♂️'], prefix='/user')


@router.post("/", status_code=status.HTTP_201_CREATED)
def register(request: UserRegister, db:Session = Depends(get_db)):
    return user_rep.register(request, db)



@router.get("/",response_model= List[UserShowAll],status_code=status.HTTP_200_OK)
def get_users(
    db: Session = Depends(get_db),
    sort_by_subscription: Literal["З підписокою", "Без підписки", "Просрочено"] | None = Query(None, description="Фільтрація за наявністю підписки"),
    sort_by_rating: Literal["За зростанням", "За спаданням"] | None = Query(None, description="Фільтраця за рейтингом"),
    sort_by_name:  Literal["А-Я", "Я-А"] | None = Query(None, description="Фільтрація за алфавітом"),
    sort_by_blocking:  Literal["Заблоковані", "Не заблоковані"] | None = Query(None, description="Фільтрація за blocking"),
    current_user:dict = Depends(get_current_user)
):

    return user_rep.get_users(db= db, sort_by_subscription=sort_by_subscription, sort_by_rating=sort_by_rating, sort_by_name=sort_by_name,sort_by_blocking = sort_by_blocking ,current_user=current_user)

@router.put("/me/", status_code=status.HTTP_200_OK)
def update_info(
    email: EmailStr = Form(...),
    name: Annotated[str, MinLen(3), MaxLen(35)] = Form(...),
    surname: Annotated[str, MinLen(3), MaxLen(35)] = Form(...),
    image: UploadFile | str | None = File(None),
    db: Session = Depends(get_db),
    current_user: dict = Depends(get_current_user)
):
    if image == "" or image is None:
        image = None

    return user_rep.update_profile(
        email=email,
        name=name,
        surname=surname,
        image=image,
        db=db,
        current_user=current_user
    )


@router.post("/process_payment")
async def process_payment(
    payment: PaymentRequest,
    db: Session = Depends(get_db),
    current_user: dict = Depends(get_current_user)
):
    
    if current_user["role"] != "user":
        raise HTTPException(status_code=403, detail="Недостатньо прав")

    
    user_id = current_user.get("sub")
    if not user_id:
        raise HTTPException(status_code=401, detail="Не вдалося визначити користувача")
    
    
    
    user = db.query(User).filter(User.user_id == user_id).outerjoin(User.subscription).first()

    if not user:
        raise HTTPException(status_code=404, detail="Користувач не знайдений")

    has_active_subscription = (
        user.subscription is not None and
        user.subscription.status == "Активна"
    )

    if has_active_subscription:
        raise HTTPException(status_code=429, detail="У вас вже є активна підписка.")
    
    payment_token = payment.paymentToken  

    if payment_token == "examplePaymentMethodToken":
        # 1. Створюємо підписку
        today = date.today()
        new_sub = Subscription(
            status="Активна",
            start_date=today,
            end_date=today + timedelta(days=30)
        )
        db.add(new_sub)
        db.commit()
        db.refresh(new_sub)

        # 2. Прив'язуємо користувача до підписки
        user = db.query(User).filter(User.user_id == user_id).first()
        if not user:
            raise HTTPException(status_code=404, detail="Користувача не знайдено")

        user.subscription_id = new_sub.subscription_id
        db.commit()

        return {
            "status": "success",
            "subscription_id": new_sub.subscription_id,
            "message": "Оплата пройшла успішно (тестовий режим)"
        }
    else:
        return {
            "status": "error",
            "message": "Невірний тестовий токен"
        }
        


@router.post("/block/", status_code=status.HTTP_201_CREATED)
def block_user(request: BlockUser, db: Session = Depends(get_db), current_user: dict = Depends(get_current_user)):
    return user_rep.block_user(request = request, db = db, current_user=current_user)


GOOGLE_CLIENT_ID = os.getenv("GOOGLE_CLIENT_ID")


@router.post("/auth/google/")
def google_login(request: GoogleAuthRequest, db: Session = Depends(get_db)):
    try:
        
        idinfo = id_token.verify_oauth2_token(
            request.id_token,
            google_requests.Request(),
            GOOGLE_CLIENT_ID
        )

        
        email = idinfo.get("email")
        name = idinfo.get("given_name", "")
        surname = idinfo.get("family_name", "")

        if not email:
            raise HTTPException(status_code=400, detail="Не вдалося отримати email з Google")

        
        user = db.query(User).filter(User.email == email).first()

        if user:
            if user.blocking:
                raise HTTPException(status_code=400, detail="Користувач заблокований")
        else:
            
            user = User(
                email=email,
                name=name,
                surname=surname,
                password=Hash.bcrypt("google") 
            )
            db.add(user)
            db.commit()
            db.refresh(user)

        access_token = create_access_token(data={"sub": user.user_id, "role": "user"})

        return {
            "message": "Успішна авторизація через Google",
            "access_token": access_token,
            "token_type": "bearer",
            "user": {
                "name": user.name,
                "surname": user.surname,
                "image": user.image.image_url if user.image else None
            }
        }

    except ValueError:
        raise HTTPException(status_code=401, detail="Невірний Google токен")
