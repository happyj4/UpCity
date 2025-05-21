import { Arrows } from "./arrows";
import { IconPlus } from "./plusIcon";
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

  const handleClick = () => {
    fileInputRef.current?.click();
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      const url = URL.createObjectURL(file);
      setImageUrl(url); // зберігаємо URL у стан
    }
  };

  async function sendApplicationUpdate() {
    const token = sessionStorage.getItem("access_token");
     if (!fileInputRef.current?.files[0]) {
        alert("Будь ласка, додайте фото.");
        return;
      }

    if (!selectedStar) {
      alert("Будь ласка, виберіть рейтинг.");
      return;
    }

    const formData = new FormData();
    formData.append("rating", selectedStar.toString());
    formData.append("status", "Виконано");

    if (fileInputRef.current?.files[0]) {
      const file = fileInputRef.current.files[0];
      console.log("Файл:", file.name, file.type, file.size);
      formData.append("image", file);
    }

    for (let pair of formData.entries()) {
      console.log(`${pair[0]}:`, pair[1]);
    }

    try {
      const response = await fetch(
        `http://46.101.245.42/application/${info.application_id}/complete`,
        {
          method: "PUT",
          headers: {
            Authorization: `Bearer ${token}`,
          },
          body: formData,
        },
      );

      if (!response.ok) {
        const errorText = await response.text();
        console.error("Помилка з серверу:", errorText);
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const result = await response.json();
      console.log("Успішно надіслано:", result);
    } catch (error) {
      console.error("Помилка при надсиланні:", error);
    }
  }

  console.log("dsds", info);

  if (!info) {
    return <div></div>;
  }

  return (
    <div className="flex-col w-[35%] h-full px-8  overflow-y-auto bg-[#FBF9F] flex-wrap">
      <div className="w-full h-auto justify-between flex">
        <h4 className="text-[#3A3A3A] text-xl font-light">#1-2634</h4>
        <Arrows />
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
        Денисенко Єлизавета
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
        <div className="bg-[#FBF0E5] w-18 h-6 flex rounded-md  content-center items-center gap-1 px-1 mb-8">
          <div className="w-1 h-1 rounded-4xl bg-[#957A5E]"></div>
          <p className="text-[#957A5E] text-sm font-normal">{info.status}</p>
        </div>
        <div className="bg-[#EDEDED] w-22 h-6 flex rounded-md  content-center items-center gap-1 px-1">
          <p className="text-[#848484] text-sm font-normal">
            {info.application_date.slice(0, 10)}
          </p>
        </div>
        <div></div>
      </div>
      <span className="text-[#3A3A3A] text-base font-normal">Фото</span>
      <Image
        className="mt-4 mb-4"
        src={info.image.image_url}
        alt="fon"
        width={391}
        height={132}
        unoptimized
      />
      <span className="text-[#3A3A3A] text-base font-normal">
        Додати фотозвіт
      </span>
      <div className="flex cursor-pointer" onClick={handleClick}>
        <div className="relative">
          <Image
            className="mt-4"
            src={imageUrl || "/images/Fon.png"}
            alt="fon"
            width={391}
            height={132}
            unoptimized
          />
          <div className={imageUrl ? "hidden" : "block"}>
            <IconPlus />
          </div>
          {/* прихований input */}
          <input
            type="file"
            accept="image/*"
            ref={fileInputRef}
            onChange={handleFileChange}
            style={{ display: "none" }}
          />
        </div>
      </div>
      <button
        className="w-96 mb-15 h-14 bg-[#FFBE7D]/70 hover:bg-[#FFBE7D] transition-all duration-300 rounded-2xl uppercase text-white text-xl font-bold shadow-md hover:shadow-lg tracking-wide mt-10 transform hover:scale-105 active:scale-95 cursor-pointer"
        onClick={() => setShowForm("block")}
      >
        змінити статус на виконано
      </button>
      <div
        className={`${showForm} fixed inset-0 bg-black/50 flex items-center justify-center z-50`}
      >
        <div className="w-130 p-6 bg-white rounded-2xl shadow-lg flex flex-col items-center">
          <h2 className="text-xl font-semibold text-[#3A3A3A] mb-4 uppercase">
            Якість/Доцільність звернення
          </h2>

          {/* ⭐️ Зірки рейтингу (можеш замінити на компонент або svg) */}
          <div className="flex gap-2 mb-6">
            {[1, 2, 3, 4, 5].map((num) => (
              <span
                key={num}
                className="text-6xl text-yellow-400 cursor-pointer transition-transform transform hover:scale-110"
                onMouseEnter={() => {
                  if (selectedStar === 0) setHoveredStar(num);
                }}
                onMouseLeave={() => {
                  if (selectedStar === 0) setHoveredStar(0);
                }}
                onClick={() => {
                  setSelectedStar(num);
                }}
              >
                {num <= (selectedStar || hoveredStar) ? "★" : "☆"}
              </span>
            ))}
          </div>

          <button
            className="w-full bg-[#FFBE7D] hover:bg-[#ffa94d] text-white font-bold py-2 px-4 rounded-lg transition-all duration-300 cursor-pointer"
            onClick={() => {
              sendApplicationUpdate();
              setShowForm("hidden");
            }}
          >
            Надіслати
          </button>
        </div>
      </div>
    </div>
  );
}
