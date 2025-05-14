from typing import Literal

from fastapi import HTTPException, status, UploadFile
from sqlalchemy import asc, desc
from sqlalchemy.orm import Session

from project.db.models import User, Blocking, Subscription
from project.repository.image_rep import upload
from project.schemas.user_schemas import UserRegister, BlockUser
from project.hashing import Hash
from project.jwt_handler import create_access_token


def register(request: UserRegister, db: Session):
    existing_user = db.query(User).filter(User.email == request.email).first()
    
    if existing_user:
        if existing_user.blocking:
            raise HTTPException(status_code=400, detail="Користувач уже заблокований")
        raise HTTPException(
            status_code=status.HTTP_409_CONFLICT,
            detail=f"Користувач з email = {request.email} вже існує"
        )

    new_user = User(
        email=request.email,
        name=request.name,
        surname=request.surname,
        password=Hash.bcrypt(request.password)
    )
    db.add(new_user)
    db.commit()
    db.refresh(new_user)  

    access_token = create_access_token(data={"sub": new_user.user_id, "role": "user"})
    
    return {
        "message": "Успішна реєстрація",
        "access_token": access_token,
        "token_type": "bearer",
        "user": {
            "name": new_user.name,
            "surname": new_user.surname,
            "image": new_user.image.image_url if new_user.image else None
        }
    }



def block_user(request: BlockUser, db: Session, current_user:dict):
    if current_user["role"] != "admin":
        raise HTTPException(status_code=403, detail="Недостатньо прав")
    user = db.query(User).filter(User.user_id == request.user_id).first()
    if not user:
        raise HTTPException(status_code=404, detail="Користувача не знайдено")

    if user.blocking:
        raise HTTPException(status_code=400, detail="Користувач уже заблокований")

    block = Blocking(
        user_id=request.user_id,
        reason=request.reason
    )
    db.add(block)
    db.commit()
    db.refresh(block)
    return {"message": "Користувача заблоковано"}

def get_users(
    db: Session,
    sort_by_subscription: Literal["З підписокою", "Без підписки", "Просрочено"] | None,
    sort_by_rating: Literal["За зростанням", "За спаданням"] | None,
    sort_by_name: Literal["А-Я", "Я-А"] | None,
    current_user:dict
):
    if current_user["role"] != "admin":
        raise HTTPException(status_code=403, detail="Недостатньо прав")
    query = db.query(User)
    
    if sort_by_subscription == "З підписокою":
        query = query.join(User.subscription).filter(Subscription.status == "Активна")
    elif sort_by_subscription == "Без підписки":
        query = query.filter(User.subscription_id == None)
    elif sort_by_subscription == "Просрочено":
        query = query.join(User.subscription).filter(Subscription.status == "Неактивна")

    
    if sort_by_rating == "За зростанням":
        query = query.order_by(asc(User.rating))
    elif sort_by_rating == "За спаданням":
        query = query.order_by(desc(User.rating))
    
    if sort_by_name == "А-Я":
        query = query.order_by(asc(User.name))
        
    elif sort_by_name == "Я-А":
        query = query.order_by(desc(User.name))
        
    users = query.all()
    return users

def update_profile(email: str, name: str, surname: str,
                   image: UploadFile | None, db: Session, current_user: dict):
    if current_user["role"] != "user":
        raise HTTPException(status_code=403, detail="Недостатньо прав")

    user_id = current_user.get("sub")
    if not user_id:
        raise HTTPException(status_code=401, detail="Не вдалося визначити користувача")

    user = db.query(User).filter(User.user_id == user_id).first()
    if not user:
        raise HTTPException(status_code=404, detail="Користувача не знайдено")

    existing_user = db.query(User).filter(User.email == email).first()

    if existing_user and existing_user.user_id != user.user_id:
        if existing_user.blocking:
            raise HTTPException(status_code=400, detail="Користувач з таким email заблокований")
        raise HTTPException(
            status_code=status.HTTP_409_CONFLICT,
            detail=f"Користувач з email = {email} вже існує"
        )



    user.email = email
    user.name = name
    user.surname = surname

    if image:
        upload_result = upload(image)
        image_id = upload_result.get("image_id")
        if image_id:
            user.image_id = image_id

    db.commit()
    db.refresh(user)

    return {
        "message": "Дані оновлено",
        "user": {
            "name": user.name,
            "surname": user.surname,
            "email": user.email,
            "image": user.image.image_url if user.image else None
        }
    }

