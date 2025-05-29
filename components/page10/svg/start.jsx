import Image from "next/image";

export function Start() {
  return (
    <div className="w-full h-auto pt-40 pl-18 flex flex-col">
      <h1 className="text-[#000] text-4xl font-light uppercase">
        Lorem ipsum arcu
      </h1>
      <div className="w-full h-auto flex gap-6 mt-10">
        <div className="relative w-[40%]">
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
        <button
          className="w-[48px] h-[48px] bg-[#E2B990] drop-shadow-lg rounded-md flex items-center justify-center 
                    transition-all duration-200 ease-in-out hover:bg-[#ffcc97] hover:scale-105 cursor-pointer"
        >
          <Image
            src="/images/Filter.svg"
            alt="filter"
            width={23}
            height={26}
            unoptimized
          />
        </button>
      </div>
    </div>
  );
}
