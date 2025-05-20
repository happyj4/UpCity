import Image from "next/image";

export function MiddlePage() {
  return (
    <div className="w-screen h-140 flex px-20 gap-10 pl-25">
      <div className="w-55 h-auto items-center flex-col">
        <div className="w-[100%] h-24 bg-white rounded-lg drop-shadow-lg mb-8 py-4 px-6 place-items-start flex-col text-[#1E1E1E] transition-transform duration-300 hover:scale-105 hover:shadow-2xl">
          <p className="text-lg font-normal">Ями на дорозі</p>
          <p className="font-normal text-2xl">
            564 / <span className="font-light text-lg">6.09%</span>
          </p>
        </div>
        <div className="w-[100%] h-51 bg-white rounded-lg drop-shadow-lg mb-8 py-4 px-6 place-items-start flex-col text-[#1E1E1E] transition-transform duration-300 hover:scale-105 hover:shadow-2xl">
          <p className="font-normal text-base mb-2">
            Всього виконаних звернень
          </p>
          <p className="font-normal text-2xl">
            2047/ <span className="font-light text-lg">2691</span>
          </p>
          <p className="text-[#339C27] text-base font-light mb-4">-5.93%</p>
          <p className="text-base font-semibold">Порівняно з минулим місяцем</p>
        </div>
        <div className="w-[100%] h-24 bg-white rounded-lg drop-shadow-lg mb-8 py-4 px-6 place-items-start flex-col text-[#1E1E1E] transition-transform duration-300 hover:scale-105 hover:shadow-2xl">
          <p className="font-normal text-base ">Відсутнє світло</p>
          <p className="font-normal text-2xl">
            742 / <span className="font-light text-lg">12.1%</span>
          </p>
        </div>
      </div>
      <div className="w-193 h-115 drop-shadow-2xl rounded-xl overflow-hidden">
        <iframe
          src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3151.8354345094743!2d144.95373531568056!3d-37.81627917975167!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x6ad642af0f11fd81%3A0xf577c0f0b3f3f57a!2sFederation%20Square!5e0!3m2!1sen!2sau!4v1614649073702!5m2!1sen!2sau"
          width="100%"
          height="100%"
          style={{ border: 0 }}
          allowFullScreen={true}
          loading="lazy"
          referrerPolicy="no-referrer-when-downgrade"
        ></iframe>
      </div>
      <div className="w-55 h-auto place-items-center flex-col ">
        <div className="w-[100%] h-33 bg-white rounded-lg drop-shadow-lg mb-8 px-2 py-2 transition-transform duration-300 hover:scale-105 hover:shadow-2xl ">
          <p className="text-[#3A3A3A] text-base font-normal mb-2">
            Найпопулярніші звернення
          </p>
          <div className="w-[100%] h-auto flex items-center gap-3 ">
            <Image
              src="/images/round.svg"
              alt="round"
              width={88}
              height={76}
              unoptimized
              quality={100}
            />
            <ul className="text-[#1E1E1E] text-xs font-medium">
              <li className="flex items-center gap-2">
                <div className="w-2 h-2 bg-[#F1F070] rounded-4xl"></div>
                Благоустрій
              </li>
              <li className="flex items-center gap-2">
                <div className="w-2 h-2 bg-[#CDCDCD] rounded-4xl"></div>Медицина
              </li>
              <li className="flex items-center gap-2">
                <div className="w-2 h-2 bg-[#A6CAEC] rounded-4xl"></div>ЖКГ
              </li>
              <li className="flex items-center gap-2">
                <div className="w-2 h-2 bg-[#F6C6AD] rounded-4xl"></div>Інше
              </li>
            </ul>
          </div>
        </div>
        <div className="w-[100%] h-75 bg-white rounded-lg drop-shadow-lg px-4 py-2 transition-transform duration-300 hover:scale-105 hover:shadow-2xl">
          <p className="text-[#1E1E1E] text-sm font-light">
            Статисткиа робіт за останні
          </p>
          <span className="text-[#1E1E1E] text-sm font-semibold">30 днів</span>
          <div className="w-[100%] place-items-center">
            <Image
              src="/images/Hystogram.png"
              alt="hystogram"
              width={141}
              height={148}
              unoptimized
              quality={100}
            />
          </div>
          <ul className="text-[#1E1E1E] text-xs font-medium">
            <li className="flex items-center gap-2 mb-1 mt-1">
              {" "}
              <div className="bg-[#C2F1C8] rounded-4xl w-2 h-2"></div>Всього
              звернень в роботі
            </li>
            <li className="flex items-center gap-2 mb-1">
              {" "}
              <div className="bg-[#A6CAEC] rounded-4xl w-2 h-2"></div>Всього
              виконаних звернень
            </li>
            <li className="flex items-center gap-2">
              {" "}
              <div className="bg-[#F2CFEE] rounded-4xl w-2 h-2"></div>Всього
              прострочених звернень
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
}
