"use client";

import { useEffect, useState } from "react";
import Image from "next/image";
import Link from "next/link";
import { Bin } from "./svg/bin";

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
      <div className="flex w-auto h-auto">
        <Image
          className="drop-shadow-2xl transition-all duration-300"
          src="/images/LogoSite.svg"
          alt="logo"
          width={isScrolled ? 120 : 166}
          height={isScrolled ? 40 : 56}
          unoptimized
        />
        <div className="w-6 h-5 flex flex-col ml-6 mt-3.5">
          <div className="w-full h-1 bg-black mb-1.5"></div>
          <div className="w-full h-1 bg-black mb-1.5"></div>
          <div className="w-full h-1 bg-black"></div>
        </div>
      </div>
      <div className="place-items-center gap-8 flex">
        <h2 className="text-[#3A3A3A] text-2xl font-light cursor-pointer">
          EN
        </h2>
        <Link
          href="#"
          className="group p-2 rounded-full transition-all duration-300 ease-in-out hover:shadow-xl hover:scale-110 bg-gray-200"
        >
          <Link href="http://localhost:3000/Bin">
            <Bin />
          </Link>
        </Link>
      </div>
    </div>
  );
}
