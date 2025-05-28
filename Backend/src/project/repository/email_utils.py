import os
from email.message import EmailMessage
import aiosmtplib
from fastapi import HTTPException
from pydantic import EmailStr
from dotenv import load_dotenv


load_dotenv()

# Берём данные из .env
SMTP_USER = os.getenv("SMTP_USER")
SMTP_PASS = os.getenv("SMTP_PASS")
SMTP_HOST = "smtp.gmail.com"
SMTP_PORT = 587

async def send_email(to_email: EmailStr, subject: str, content: str):
    message = EmailMessage()
    message["From"] = SMTP_USER
    message["To"] = to_email
    message["Subject"] = subject
    message.set_content(content)

    try:
        await aiosmtplib.send(
            message,
            hostname=SMTP_HOST,
            port=SMTP_PORT,
            username=SMTP_USER,
            password=SMTP_PASS,
            start_tls=True
        )
    except aiosmtplib.errors.SMTPAuthenticationError:
        raise HTTPException(status_code=500, detail="SMTP authentication failed")
    except Exception:
        raise HTTPException(status_code=500, detail="Internal server error while sending email")
