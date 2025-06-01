"use client";
import { useRouter } from 'next/router'
import { useEffect, useState } from "react";
import Image from "next/image";
import Link from "next/link";

export function Headeradmin() {
  const [isScrolled, setIsScrolled] = useState(false);
  const [admin, setAdmin] = useState(null);
  const [open, setOpen] = useState(false);
  const router = useRouter()

  useEffect(() => {
    const storedAdmin = sessionStorage.getItem("admin_surname");
    setAdmin(storedAdmin);
  }, []);

  useEffect(() => {
    const handleScroll = () => {
      if (window.scrollY > 20) {
        setIsScrolled(true);
      } else {
        setIsScrolled(false);
      }
    };
    window.addEventListener("scroll", handleScroll);

    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  function closing(){
    sessionStorage.setItem("access_token", "")
    sessionStorage.setItem("admin_surname", "")
    sessionStorage.setItem("Admin", "")
    router.push('/registration') // або інший маршрут
  }

  return (
    <div
      className={`fixed top-0 left-1/2 w-[95%] z-50 transition-all duration-300 ${
        isScrolled
          ? "h-20 w-full shadow-xl bg-white/95 backdrop-blur-md"
          : "h-25 shadow-md bg-white"
      } rounded-b-2xl flex px-8 place-items-center justify-between transform -translate-x-1/2`}
    >
      
        <Image
          className="drop-shadow-2xl transition-all duration-300"
          src="/images/LogoSite.svg"
          alt="logo"
          width={isScrolled ? 120 : 166} // Логотип теж трохи зменшується
          height={isScrolled ? 40 : 56}
          unoptimized
        />
      
      <div className="gap-12 flex place-items-center text-sm not-italic uppercase cursor-pointer">
        <Link
          href="https://up-city-8xew.vercel.app/appeal"
          className="transition-all duration-300 hover:text-[#FFBE7D] hover:scale-110"
        >
          <span>звернення громадян</span>
        </Link>
        <Link
          href="https://up-city-8xew.vercel.app/list_kp"
          className="transition-all duration-300 hover:text-[#FFBE7D] hover:scale-110"
        >
          <span>список кп</span>
        </Link>
        <Link
          href="https://up-city-8xew.vercel.app/list_people"
          className="transition-all duration-300 hover:text-[#FFBE7D] hover:scale-110"
        >
          <span>список громадян</span>
        </Link>
      </div>
      <div className="relative inline-block text-left">
        {/* Головна кнопка */}
        <button
          onClick={() => setOpen(!open)}
          className="w-47 h-15 flex flex-col cursor-pointer bg-[#FFBE7D] rounded-lg items-center justify-center transition-all duration-300 hover:bg-[#dca36b] hover:drop-shadow-2xl hover:scale-105 active:scale-95 px-4 py-3"
        >
          <h2 className="text-[#FFF] text-xl font-semibold">Адміністратор</h2>
          <p className="text-[#FFF] font-normal text-base">{admin}</p>
        </button>

        {open && (
          <div className="absolute right-0 mt-2 w-40 bg-white rounded-xl shadow-xl z-10 p-2">
            <button
              onClick={()=>closing()}
              className="w-full cursor-pointer bg-red-500 text-white text-center text-md font-bold px-4 py-2 rounded-md transition-all duration-200 hover:bg-red-600 hover:shadow-md active:scale-95"
            >
              Вийти
            </button>
          </div>
        )}
      </div>
    </div>
  );
}
