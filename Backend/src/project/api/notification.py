from fastapi import APIRouter

from project.repository.email_utils import send_email
from project.schemas.user_schemas import EmailRequest

router = APIRouter(tags=['Повідомлення 📢'])

@router.post("/send-email")
async def send_email_endpoint(req: EmailRequest):
    await send_email(req.to, req.subject, req.message)
    return {"status": "ok"}