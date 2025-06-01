"use client";

import { useEffect, useState } from "react";
import Image from "next/image";
import Link from "next/link";

export function Header() {
  const [isScrolled, setIsScrolled] = useState(false);

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
      <Link href="https://up-city-8xew.vercel.app">
        <Image
          className="drop-shadow-2xl transition-all duration-300"
          src="/images/LogoSite.svg"
          alt="logo"
          width={isScrolled ? 120 : 166} // Логотип теж трохи зменшується
          height={isScrolled ? 40 : 56}
          unoptimized
        />
      </Link>
      <div className="gap-12 flex place-items-center text-sm not-italic uppercase cursor-pointer">
        <Link
          href="https://up-city-8xew.vercel.app"
          className="transition-all duration-300 hover:text-[#FFBE7D] hover:scale-110"
        >
          <span>головна</span>
        </Link>
        <Link
          href="https://up-city-8xew.vercel.app/about_us"
          className="transition-all duration-300 hover:text-[#FFBE7D] hover:scale-110"
        >
          <span>про нас</span>
        </Link>
        <Link
          href="https://up-city-8xew.vercel.app/appeal"
          className="transition-all duration-300 hover:text-[#FFBE7D] hover:scale-110"
        >
          <span>звернення громадян</span>
        </Link>
      </div>
      <div className="place-items-center gap-2 flex">

        <Link href="https://up-city-8xew.vercel.app/registration">
          <button className="w-35 h-12 cursor-pointer place-items-center p-1 rounded-md border font-semibold text-lg text-center border-solid border-[#848484] text-[#848484] transition-all duration-300 hover:border-[#FFBE7D] hover:text-[#FFBE7D] hover:bg-[#fff8f0] hover:shadow-md">
            Увійти
          </button>
        </Link>
      </div>
    </div>
  );
}
