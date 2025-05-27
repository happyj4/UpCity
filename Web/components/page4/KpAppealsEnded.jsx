import { Arrows } from "./arrows";
import Image from "next/image";
import { useEffect, useState } from "react";
import { useRef } from "react";

export function KpAppeals() {
  const [info, setInfo] = useState(null);
  const [hoveredStar, setHoveredStar] = useState(0);
  const [selectedStar, setSelectedStar] = useState(0);
  const [showForm, setShowForm] = useState("hidden");
  const fileInputRef = useRef(null);
  const [imageUrl, setImageUrl] = useState(null);
  const [check, setCheck] = useState("")

   useEffect(() => {
    const isKp = sessionStorage.getItem("KP");
    if (isKp) {
      setCheck("kp");
    } else {
      setCheck("");
    }
  }, []);

  useEffect(() => {
    const token = sessionStorage.getItem("access_token");
    const hash = window.location.hash; // #28
    const id = hash ? hash.replace("#", "") : null;

    console.log(id);

    if (id) {
      fetch(`http://46.101.245.42/application/${id}`, {
        headers: {
          Accept: "application/json",
          Authorization: `Bearer ${token}`,
        },
      })
        .then((res) => res.json())
        .then((data) => setInfo(data))
        .catch((err) => console.error("Error:", err));
    }
  }, []);

  useEffect(() => {
    if (info?.user_rating) {
      setSelectedStar(info.user_rating);
    }
  }, [info]);

  console.log("dsds", info);

  if (!info) {
    return <div></div>;
  }

  return (
    <div className="flex-col w-[35%] h-full px-8  overflow-y-auto bg-[#FBF9F] flex-wrap">
      <div className="w-full h-auto justify-between flex">
        <h4 className="text-[#3A3A3A] text-xl font-light">#1-2634</h4>
       {check ? <Arrows /> : ""}
      </div>
      <span className="text-[#3A3A3A] text-base font-light">Назва</span>
      <h1 className="text-[#3A3A3A] text-4xl font-semibold mb-3">
        {info.name}
      </h1>
      <span className="text-[#3A3A3A] text-base font-light">Адреса</span>
      <h3 className="text-[#3A3A3A] font-normal text-xl mb-3">
        {info.address}
      </h3>
      <div className="flex gap-8 mb-8">
        <div className="flex-col">
          <span className="text-[#3A3A3A] text-base font-light">Довгота</span>
          <h3 className="text-[#3A3A3A] font-normal text-xl">
            {info.longitude}
          </h3>
        </div>
        <div className="w-[1px] h-13 bg-black"></div>
        <div className="flex-col">
          <span className="text-[#3A3A3A] text-base font-light">Широта</span>
          <h3 className="text-[#3A3A3A] font-normal text-xl">
            {info.latitude}
          </h3>
        </div>
      </div>
      <span className="text-[#3A3A3A] text-base font-light">Користувач</span>
      <h3 className="text-[#3A3A3A] font-normal text-xl mb-3">
        {info.user.name}
      </h3>
      <span className="text-[#3A3A3A] text-base font-light">КП</span>
      <h3 className="text-[#3A3A3A] font-normal text-xl mb-6">
        КП «{info.utility_company.name}»
      </h3>
      <span className="text-[#3A3A3A] text-base font-light">Опис</span>
      <h3 className="text-[#3A3A3A] font-normal text-xl mb-6">
        {info.description}
      </h3>
      <div className="flex gap-4">
        <div className={` ${info.status === "Виконано" ? "bg-[#EBFFEE]" : "bg-[#EDEDED]"} w-18 h-6 flex rounded-md  content-center items-center gap-1 px-1 mb-8`}>
          <div className={`${info.status === "Виконано" ? "bg-[#EBFFEE]" : "text-[#EDEDED]"} w-1 h-1 rounded-4xl`}></div>
          <p className={`${(info.status === "Виконано")? "text-[#589D51]": "text-[#848484]"} text-sm font-normal`}>{info.status}</p>
        </div>
        <div className="bg-[#EDEDED] w-22 h-6 flex rounded-md  content-center items-center gap-1 px-1">
          <p className="text-[#848484] text-sm font-normal">
            {info.application_date.slice(0, 10)}
          </p>
        </div>
        <div></div>
      </div>
      <span className="text-[#3A3A3A] text-base font-normal">Фото до</span>
      <Image
        className="mt-4 mb-4"
        src={info.image.image_url}
        alt="fon"
        width={391}
        height={132}
        unoptimized
      />
      <span className="text-[#3A3A3A] text-base font-normal">Фото після</span>
      <div className="flex cursor-pointer">
        <div className="relative">
          <Image
            className="mt-4"
            src={(info.report.image)? info.report.image.image_url : "/images/Fon.png"}
            alt="fon"
            width={391}
            height={132}
            unoptimized
          />
        </div>
      </div>
      <div className="w-[100%] bg-[#FBF9F4] p-6 mt-5  flex flex-col items-center">
        <h2 className="text-medium font-semibold text-[#3A3A3A] mb-4 uppercase">
          Якість/Доцільність звернення
        </h2>

        {/* ⭐️ Зірки рейтингу (можеш замінити на компонент або svg) */}
        <div className="flex gap-2 mb-6">
          {[1, 2, 3, 4, 5].map((num) => (
            <span key={num} className="text-6xl text-yellow-400 cursor-pointer">
              {num <= (selectedStar || hoveredStar) ? "★" : "☆"}
            </span>
          ))}
        </div>
      </div>
    </div>
  );
}
