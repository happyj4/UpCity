import Image from "next/image";
import Link from "next/link";

export function Footer() {
  return (
    <div className="w-100% h-74 flex border-t-3 border-t-[#1E1E1E] px-[8%] py-[2%] gap-10">
      <div className="flex-col w-[30%] h-[100%]">
        <div className="mb-4">
          <Image
            src="/images/LogoSite.svg"
            alt="logo"
            width={244}
            height={78}
            unoptimized
          />
        </div>
        <ul className="uppercase text-[#3A3A3A] tex-xl font-semibold ">
          <Link
            href="http://localhost:3000"
            className="transition-all duration-300 hover:text-[#FFBE7D] hover:scale-110"
          >
            <li className="mb-2">головна</li>
          </Link>
          <Link
            href="http://localhost:3000/about_us"
            className="transition-all duration-300 hover:text-[#FFBE7D] hover:scale-110"
          >
            <li className="mb-2">про нас</li>
          </Link>
          <Link
            href="http://localhost:3000/appeal"
            className="transition-all duration-300 hover:text-[#FFBE7D] hover:scale-110"
          >
            <li className="mb-2">звернення громадян</li>
          </Link>
          <Link
            href="http://localhost:3000/list_kp"
            className="transition-all duration-300 hover:text-[#FFBE7D] hover:scale-110"
          >
            <li>список кп</li>
          </Link>
        </ul>
      </div>
      <div className="flex-col w-[30%] h-[100%] pt-4 text-[#3A3A3A]">
        <h1 className="uppercase text-3xl font-semibold">Якщо є запитання</h1>
        <Image
          src="/images/Vector.svg"
          alt="vector"
          width={308}
          height={32}
          unoptimized
        />
        <p className="mt-5 font-normal text-base">Служба підтримки</p>
        <p className="text-2xl font-semibold">+380 (67) 777 00 77</p>
        <p className="mt-2 font-normal text-base">Email</p>
        <p className="text-2xl font-semibold">upcity@gmail.com</p>
      </div>
      <div className="flex-col w-[30%] h-[100%] pt-4 text-[#3A3A3A] ">
        <p className="uppercase text-3xl font-semibold pl-12">
          слідкуйте за нами
        </p>
        <Image
          className="ml-12"
          src="/images/Vector.svg"
          alt="vector"
          width={308}
          height={32}
          unoptimized
        />
        <div className="w-[100%] h-auto flex">
          <Image
            className="pt-16"
            src="/images/Star.svg"
            alt="vector"
            width={80}
            height={80}
            unoptimized
          />
          <Image
            className="relative right-14 top-8"
            src="/images/Telegram.svg"
            alt="telegram"
            width={30}
            height={25}
            unoptimized
          />
          <Image
            className="pb-10"
            src="/images/Star.svg"
            alt="vector"
            width={65}
            height={65}
            unoptimized
          />
          <Image
            className="relative right-12 bottom-5"
            src="/images/Instagram.svg"
            alt="instagram"
            width={31}
            height={31}
            unoptimized
          />
          <Image
            className="mt-13"
            src="/images/Star.svg"
            alt="vector"
            width={90}
            height={90}
            unoptimized
          />
          <Image
            className="relative right-16 top-6"
            src="/images/Youtube.svg"
            alt="youtube"
            width={40}
            height={28}
            unoptimized
          />
          <Image
            className=" mb-12"
            src="/images/Star.svg"
            alt="vector"
            width={90}
            height={90}
            unoptimized
          />
          <Image
            className="relative right-17 bottom-6"
            src="/images/Facebook.svg"
            alt="facebook"
            width={54}
            height={54}
            unoptimized
          />
        </div>
      </div>
    </div>
  );
}
