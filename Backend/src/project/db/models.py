from sqlalchemy import Column, Integer, String, ForeignKey, Date, Text, TIMESTAMP, DECIMAL, CheckConstraint, func
from .database import Base
from sqlalchemy.orm import relationship
from datetime import datetime


class Utility_Company(Base):
  __tablename__ = 'utility_company'

  ut_company_id = Column(Integer, primary_key=True , index= True)
  name = Column(String(255))
  address = Column(String(255))
  phone = Column(String(50))
  email = Column(String(255), unique=True, nullable=False)
  password = Column(String(255))

  applications = relationship("Application", back_populates="utility_company")
  #done



class Subscription(Base):
  __tablename__ = 'subscription'

  subscription_id = Column(Integer, primary_key=True, index=True)
  status = Column(String(50))
  start_date = Column(Date)
  end_date = Column(Date)

  user = relationship("User", back_populates="subscription")

  __table_args__ = (
        CheckConstraint("status IN ('Активна', 'Неактивна', 'Скасована')", name="check_subscription_status"),
    )
  #done
  


class Image(Base):
  __tablename__ = 'image'

  image_id = Column(Integer, primary_key=True, index=True)
  image_url = Column(Text, nullable=False)
  image_date = Column(TIMESTAMP, server_default=func.now())
  
  user = relationship("User", back_populates="image",  uselist=False)
  reports = relationship("Report", back_populates="image")
  #done




class User(Base):
  __tablename__ = 'user'

  user_id  = Column(Integer, primary_key=True, index=True)
  name  = Column(String(100))
  surname  = Column(String(100))
  email  = Column(String(255), unique=True, nullable=False)
  password = Column(String(255))
  subscription_id = Column(Integer, ForeignKey("subscription.subscription_id"))
  image_id = Column(Integer, ForeignKey("image.image_id"))

  subscription = relationship("Subscription", back_populates="user", uselist=False)
  blocking = relationship("Blocking", back_populates="user",  uselist=False)
  image = relationship("Image", back_populates="user",  uselist=False)
  applications = relationship("Application", back_populates="user")
  #done





class Report(Base):
  __tablename__ = 'report'


  report_id  = Column(Integer, primary_key=True, index=True)

  image = relationship("Image", back_populates="reports")

  #закончить


class Application(Base):
  __tablename__ = 'application'

  application_id  = Column(Integer, primary_key=True, index=True)
  name  = Column(String(255))
  description  = Column(Text)
  address  = Column(String(255))
  ut_company_id = Column(Integer, ForeignKey("utility_company.ut_company_id"))

  utility_company = relationship("Utility_Company", back_populates="applications ")
  user= relationship("User", back_populates="applications")


  #закончить








class Blocking(Base):
  __tablename__ = 'blocking'

  blocking_id  = Column(Integer, primary_key=True, index=True)
  user_id = Column(Integer, ForeignKey("user.user_id"))
  reason = Column(Text)
  block_date = Column(TIMESTAMP, server_default=func.now())

  user = relationship("User", back_populates="blocking")
  #done






class Admin(Base):
  __tablename__ = 'admin'

  admin_id = Column(Integer, primary_key=True, index=True)
  name = Column(String(100))
  surname = Column(String(100))
  email = Column(String(255), unique=True, nullable=False)
  password = Column(String(255))
  #done





