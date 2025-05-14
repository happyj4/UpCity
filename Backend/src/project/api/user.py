from typing import List, Literal, Annotated
from datetime import date, timedelta

from fastapi import APIRouter, Depends, status, Query, Path, UploadFile, Form, File, Request, HTTPException
from sqlalchemy.orm import Session
from annotated_types import MinLen, MaxLen
from pydantic import EmailStr


from project.db.database import get_db
from project.db.models import Subscription, User
from project.schemas.user_schemas import UserRegister , UserShowAll , BlockUser, PaymentRequest
from project.repository import user_rep
from project.oauth2 import get_current_user


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
    current_user:dict = Depends(get_current_user)
):

    return user_rep.get_users(db= db, sort_by_subscription=sort_by_subscription, sort_by_rating=sort_by_rating, sort_by_name=sort_by_name, current_user=current_user)

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

    
    payment_token = payment.paymentToken  

    if payment_token == "test_googlepay_token":
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
