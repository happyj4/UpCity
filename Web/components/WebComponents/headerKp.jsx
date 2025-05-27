"use client";

import { useEffect, useState } from "react";
import Image from "next/image";
import Link from "next/link";

export function HeaderKP() {
  const [isScrolled, setIsScrolled] = useState(false);
  const [KP, setKP] = useState(null);

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

  return (
    <div
      className={`fixed top-0 left-1/2 w-[95%] z-50 transition-all duration-300 ${
        isScrolled
          ? "h-20 w-full shadow-xl bg-white/95 backdrop-blur-md"
          : "h-25 shadow-md bg-white"
      } rounded-b-2xl flex px-8 place-items-center justify-between transform -translate-x-1/2`}
    >
      <Link href="http://localhost:3000">
        <Image
          className="drop-shadow-2xl transition-all duration-300"
          src="/images/LogoSite.svg"
          alt="logo"
          width={isScrolled ? 120 : 166} // Логотип теж трохи зменшується
          height={isScrolled ? 40 : 56}
          unoptimized
        />
      </Link>
      <div className="place-items-center  flex">
        <button className="w-auto h-12 flex flex-col cursor-pointer bg-[#FFBE7D] px-2 rounded-lg  items-center justify-center transition-all duration-300 hover:bg-[#dca36b] hover:drop-shadow-2xl hover:scale-105 active:scale-95">
          <h2 className="text-[#FFF] text-xl font-semibold">КП "{KP}"</h2>
        </button>
      </div>
    </div>
  );
}
