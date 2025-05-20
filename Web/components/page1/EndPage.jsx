import Image from "next/image";

export function EndPage() {
  return (
    <div className="w-screen h-140 flex px-20 gap-10 pl-[8%] mb-10">
      <div className="w-143 h-[100%] pt-30">
        <div className="break-words w-110">
          <p className="text-[#1E1E1E] text-5xl font-bold uppercase tracking-widest">
            Завантажте
          </p>
          <span className="font-normal text-[#1E1E1E] text-4xl uppercase">
            наш додаток
          </span>
        </div>
        <div className="w-[90%] h-auto break-words mt-8 mb-20">
          <p className="text-[#3A3A3A] text-base font-light">
            Lorem ipsum dolor sit amet consectetur. Dis mus ultrices gravida
            lacinia vehicula pretium aliquam ultrices phasellus. Semper volutpat
            dignissim velit blandit vitae sed ut nunc. Id neque placerat urna a
            pharetra. Et viverra pretium a penatibus sed.
          </p>
        </div>
        <Image
          className="cursor-pointer hover:scale-120 transition-transform duration-300 drop-shadow-2xl "
          src="/images/button-download.png"
          alt="download"
          width={186}
          height={61}
          unoptimized
          quality={100}
        />
      </div>
      <div className="w-136 h-140 drop-shadow-2xl">
        <Image
          src="/images/Redmi_Note_11.png"
          alt="redminote"
          width={545}
          height={559}
          unoptimized
          quality={100}
        />
      </div>
    </div>
  );
}
