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
      <div className="gap-12 flex place-items-center text-sm not-italic uppercase cursor-pointer">
        <Link
          href="http://localhost:3000"
          className="transition-all duration-300 hover:text-[#FFBE7D] hover:scale-110"
        >
          <span>головна</span>
        </Link>
        <Link
          href="http://localhost:3000/about_us"
          className="transition-all duration-300 hover:text-[#FFBE7D] hover:scale-110"
        >
          <span>про нас</span>
        </Link>
        <Link
          href="http://localhost:3000/appeal"
          className="transition-all duration-300 hover:text-[#FFBE7D] hover:scale-110"
        >
          <span>звернення громадян</span>
        </Link>
        <Link
          href="http://localhost:3000/list_kp"
          className="transition-all duration-300 hover:text-[#FFBE7D] hover:scale-110"
        >
          <span>список кп</span>
        </Link>
        <Link
          href="http://localhost:3000/list_people"
          className="transition-all duration-300 hover:text-[#FFBE7D] hover:scale-110"
        >
          <span>список громадян</span>
        </Link>
      </div>
      <div className="place-items-center gap-2 flex">
        <button className="w-10 h-9.5 cursor-pointer bg-[#FFBE7D] rounded-lg flex items-center justify-center transition-all duration-300 hover:bg-[#dca36b] hover:drop-shadow-2xl hover:scale-105 active:scale-95">
          <Image
            src="/images/plusIcon.svg"
            alt="plus"
            width={16}
            height={15}
            unoptimized
            quality={100}
          />
        </button>
        <Link href="http://localhost:3000/registration">
          <button className="w-17 h-9 cursor-pointer place-items-center p-1 rounded-md border text-center border-solid border-[#848484] text-[#848484] transition-all duration-300 hover:border-[#FFBE7D] hover:text-[#FFBE7D] hover:bg-[#fff8f0] hover:shadow-md">
            Увійти
          </button>
        </Link>
      </div>
    </div>
  );
}
