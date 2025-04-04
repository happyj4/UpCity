import asyncio
from sqlalchemy.ext.asyncio import create_async_engine, async_sessionmaker, AsyncSession
from sqlalchemy.orm import Session, sessionmaker, DeclarativeBase
from sqlalchemy import URL, create_engine , text
from config import settings


async_engine =  create_async_engine(
  url = settings.DATABASE_URL_asyncpg,
  echo = False,
  #pool_size = 5 # размер подключения к базе данных будет максимально созадно для работы с алхимией
  #max_overflow=10 # вызывает 10 доп подключений если pool_size = 5  уже используется
)







session_async =  sessionmaker(async_engine)

class Base(DeclarativeBase):
  pass
