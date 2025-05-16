import { Inwork } from "../components/page5/inwork.jsx";
import { Footer } from "../components/WebComponents/footer.jsx";
import { Header } from "../components/WebComponents/header.jsx";
import Image from "next/image";

export default function () {
  return (
    <div className="w-screen h-auto bg-[#FBF9F4]">
      <Header />
      <div className="px-14 pt-40">
        <div className="w-[35%] flex gap-3 items-center">
          <div className="relative w-[90%]">
            <input
              type="text"
              className="w-full h-12 pl-12 pr-4 bg-white rounded-2xl border border-gray-200 shadow-sm focus:outline-none focus:ring-4 focus:ring-[#FFBE7D]/50 focus:border-[#FFBE7D] transition-all duration-300 hover:shadow-md text-sm text-gray-700 placeholder-gray-400"
              placeholder="Пошук..."
            />

            <Image
              src="/images/Loop.png"
              alt="loop"
              width={18}
              height={18}
              unoptimized
              className="absolute left-4 top-1/2 transform -translate-y-1/2"
            />
          </div>
          <button className="w-10 h-10 bg-[#FFBE7D] hover:bg-[#f7ad5c] transition-all duration-300 rounded-md place-content-center flex cursor-pointer">
            <Image
              src="/images/Filter.svg"
              alt="filter"
              width={22}
              height={26}
              unoptimized
            />
          </button>
        </div>
        <Inwork />
        <Footer />
      </div>
    </div>
  );
}
