import Image from "next/image";

export function StartPage() {
  return (
    <div className="w-screen h-150 flex px-8 pt-40">
      <div className="w-[15%] h-65 flex flex-col items-center mt-14">
        <div className="w-38 h-auto  break-words rotate-270 text-gray-500 text-xl font-normal tracking-[1.8px] ">
          Звітуйте про проблеми в місті
        </div>
        <Image
          className="mt-12 mb-4"
          src="/images/ArrowDown.svg"
          alt="arrowDown"
          width={32}
          height={32}
          unoptimized
        />
        <button className="w-10 h-9.5 cursor-pointer bg-[#FFBE7D] rounded-lg flex items-center justify-center transition-all duration-300 hover:bg-[#dca36b] hover:drop-shadow-2xl hover:scale-105 active:scale-95">
          <Image src="/images/plusIcon.svg" alt="plus" width={16} height={15} />
        </button>
      </div>
      <div className="w-[40%] h-auto">
        <h1 className="break-words text-[#1E1E1E] text-6xl font-bold">
          Допомагаємо поліпшити якість життя{" "}
        </h1>
        <p className="mt-6 text-base text-[#3A3A3A] font-light">
          <span className="font-bold">UpCity</span> дозволяє громадянам
          повідомляти про проблеми з комунальними послугами, такими як
          водопостачання, опалення, освітлення вулиць тощо. Ми працюємо над тим,
          щоб зробити наше місто кращим місцем для життя.
        </p>
      </div>
      <div className="w-150 h-auto drop-shadow-2xl">
        <Image
          src="/images/ZenBook.png"
          alt="zenBook"
          width={550}
          height={300}
          unoptimized
          quality={100}
        />
        <Image
          className="relative bottom-70 right-10 "
          src="/images/Black.png"
          alt="zenBook"
          width={250}
          height={300}
          unoptimized
          quality={100}
        />
      </div>
    </div>
  );
}
