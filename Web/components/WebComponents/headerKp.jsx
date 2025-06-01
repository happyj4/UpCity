"use client";

import { useEffect, useState } from "react";
import Image from "next/image";
import Link from "next/link";
import { useRouter } from 'next/router'

export function HeaderKP() {
  const [isScrolled, setIsScrolled] = useState(false);
  const [KP, setKP] = useState(null);
  const [open, setOpen] = useState(false);
  const router = useRouter()

  useEffect(() => {
    const storedKP = sessionStorage.getItem("KP_surname");
    setKP(storedKP);
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
    sessionStorage.setItem("KP", "")
    sessionStorage.setItem("KP_surname", "")
    sessionStorage.setItem("access_token", "")
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
      
      
      <div className="place-items-center  flex">
       <button
        onClick={() => setOpen(!open)} // ← відкриває/закриває меню
        className="w-auto h-12 flex flex-col cursor-pointer bg-[#FFBE7D] px-2 rounded-lg items-center justify-center transition-all duration-300 hover:bg-[#dca36b] hover:drop-shadow-2xl hover:scale-105 active:scale-95"
      >
        <h2 className="text-[#FFF] text-xl font-semibold">КП "{KP}"</h2>
      </button>
        

        {open && (
          <div className="absolute right-0 mt-[8%] mr-[4%] w-40 bg-white rounded-xl shadow-xl z-10 p-2">
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
