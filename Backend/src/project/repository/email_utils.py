import os
from dotenv import load_dotenv
from email.message import EmailMessage
import aiosmtplib
from fastapi import APIRouter, HTTPException
from pydantic import BaseModel, EmailStr
import logging
import traceback

# Настрой логирование
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Загрузка .env
load_dotenv()

SMTP_USER = os.getenv("SMTP_USER")  # Поштова адреса відправника
SMTP_LOGIN = os.getenv("SMTP_LOGIN")  # SMTP логін
SMTP_PASS = os.getenv("SMTP_PASS")  # SMTP пароль
SMTP_HOST = "smtp-relay.brevo.com"
SMTP_PORT = 587

async def send_email(to_email: str, subject: str, content: str):
    message = EmailMessage()
    message["From"] = SMTP_USER  # Це email відправника, має бути верифікований у Brevo
    message["To"] = to_email
    message["Subject"] = subject
    message.set_content(content)

    try:
        await aiosmtplib.send(
            message,
            hostname=SMTP_HOST,
            port=SMTP_PORT,
            username=SMTP_LOGIN,     # Використовуємо правильний SMTP логін!
            password=SMTP_PASS,
            start_tls=True
        )
        logger.info(f"Email successfully sent to {to_email}")
    except aiosmtplib.errors.SMTPAuthenticationError as e:
        logger.error(f"SMTP authentication error: {e}")
        raise HTTPException(status_code=500, detail="SMTP authentication failed")
    except Exception as e:
        logger.error(f"Error sending email: {e}")
        logger.error(traceback.format_exc())
        raise HTTPException(status_code=500, detail="Internal server error while sending email")
