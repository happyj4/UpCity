import { Arrows } from "./arrows";
import { IconPlus } from "./plusIcon";
import Image from "next/image";
import { useEffect, useState } from "react";
import { useRef } from "react";
import Swal from "sweetalert2";
import { motion, AnimatePresence } from "framer-motion";
import Link from "next/link";

export function KpAppeals() {
  const [info, setInfo] = useState(null);
  const [hoveredStar, setHoveredStar] = useState(0);
  const [selectedStar, setSelectedStar] = useState(0);
  const [showForm, setShowForm] = useState("hidden");
  const fileInputRef = useRef(null);
  const [imageUrl, setImageUrl] = useState(null);
  const [check, setCheck] = useState("");

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
    const hash = window.location.hash; 
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

  const confirmApplication = async (id) => {
    const token = sessionStorage.getItem("access_token");
    try {
      const response = await fetch(
        `http://46.101.245.42/application/${id}/confirm/?status=${encodeURIComponent("В роботі")}`,
        {
          method: "PUT",
          headers: {
            accept: "application/json",
            Authorization: `Bearer ${token}`,
          },
        },
      );

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      Swal.fire("Успішно!", "Звернення успішно оброблено", "success").then(
        () => {
          window.location.href = "http://localhost:3000/kp_working";
        },
      );
    } catch (error) {
      console.error("Error confirming application:", error);
    }
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      const url = URL.createObjectURL(file);
      setImageUrl(url); 
    }
  };

  async function sendApplicationUpdate() {
    const token = sessionStorage.getItem("access_token");
    if (!fileInputRef.current?.files[0]) {
      Swal.fire("Відсутнє фото", "Будь ласка додайте фото", "error");
      return;
    }

    if (!selectedStar) {
      Swal.fire(
        "Потрібно виставити рейтинг",
        "Будь ласка оцініть звернення",
        "error",
      );
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

      Swal.fire("Успішно!", "Звернення успішно оброблено", "success").then(
        () => {
          window.location.href = "http://localhost:3000/kp_working";
        },
      );
    } catch (error) {
      Swal.fire("Упс", "Виникла помилка", "error");
      return;
    }
  }

  async function canceling() {
    const token = sessionStorage.getItem("access_token");

    const formData = new FormData();
    formData.append("rating", "1");
    formData.append("status", "Відхилено");

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

      Swal.fire("Успішно!", "Звернення відхилено", "success").then(() => {
        window.location.href = "http://localhost:3000/kp_working";
      });
    } catch (error) {
      Swal.fire("Упс", "Виникла помилка", "error");
      return;
    }
  }

  console.log("dsds", info);

  if (!info) {
    return <div></div>;
  }

  return (
    <motion.div
      className="flex-col w-[35%] h-full px-8 overflow-y-auto bg-[#FBF9F] flex-wrap"
      initial={{ opacity: 0, x: -50 }}
      animate={{ opacity: 1, x: 0 }}
      transition={{ duration: 0.5 }}
    >
      <motion.div
        className="w-full h-auto justify-between flex"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.1 }}
      >
        <h4 className="text-[#3A3A3A] text-xl font-light">
          # {info.application_id}
        </h4>
        <Link href={check ? "http://localhost:3000/kp_working" : "http://localhost:3000/appeal"}>
        <Arrows />
        </Link>
      </motion.div>
      <motion.span
        className="text-[#3A3A3A] text-base font-light"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.2 }}
      >
        Назва
      </motion.span>
      <motion.h1
        className="text-[#3A3A3A] text-4xl font-semibold mb-3"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.3 }}
      >
        {info.name}
      </motion.h1>
      <motion.span
        className="text-[#3A3A3A] text-base font-light"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.4 }}
      >
        Адреса
      </motion.span>
      <motion.h3
        className="text-[#3A3A3A] font-normal text-xl mb-3"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.5 }}
      >
        {info.address}
      </motion.h3>

      <motion.div
        className="flex gap-8 mb-8"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.6 }}
      >
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
      </motion.div>

      <motion.span
        className="text-[#3A3A3A] text-base font-light"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.7 }}
      >
        Користувач
      </motion.span>
      <motion.h3
        className="text-[#3A3A3A] font-normal text-xl mb-3"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.75 }}
      >
        {info.user.name}
      </motion.h3>
      <span className="text-[#3A3A3A] text-base font-light">КП</span>
      <h3 className="text-[#3A3A3A] font-normal text-xl mb-6">
        КП «{info.utility_company.name}»
      </h3>
      <span className="text-[#3A3A3A] text-base font-light">Опис</span>
      <h3 className="text-[#3A3A3A] font-normal text-xl mb-6">
        {info.description}
      </h3>

      <motion.div
        className="flex gap-4"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.8 }}
      >
        <div className="bg-[#EDEDED] w-[35%] h-6 flex rounded-md content-center items-center gap-1 px-1 mb-8">
          <div className="w-1 h-1 rounded-4xl bg-[#848484]"></div>
          <p className="text-[#848484] text-sm font-normal">{info.status}</p>
        </div>
        <div className="bg-[#EDEDED] w-22 h-6 flex rounded-md content-center items-center gap-1 px-1">
          <p className="text-[#848484] text-sm font-normal">
            {info.application_date.slice(0, 10)}
          </p>
        </div>
      </motion.div>

      <span className="text-[#3A3A3A] text-base font-normal">Фото</span>
      <motion.div
        className="w-full h-[30%] relative mt-[5%] mb-[5%]" 
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.9 }}
      >
        <Image
          src={info.image.image_url}
          alt="fon"
          fill 
          className="object-cover rounded-4xl" 
          unoptimized
        />
      </motion.div>

      <motion.button
        className={`w-[100%] ${check ? "block" : "hidden"}  mb-5 h-14 bg-[#FFBE7D]/70 hover:bg-[#FFBE7D] transition-all duration-300 rounded-2xl uppercase text-white text-xl font-bold shadow-md hover:shadow-lg tracking-wide mt-10 transform hover:scale-105 active:scale-95 cursor-pointer`}
        onClick={() => confirmApplication(info.application_id)}
        whileHover={{ scale: 1.05 }}
        whileTap={{ scale: 0.95 }}
      >
        Прийняти до роботи
      </motion.button>

      <motion.button
        className={`w-[100%] ${check ? "block" : "hidden"} mb-15 h-14 bg-[#FBF9F4] uppercase text-[#3A3A3A] text-lg font-medium cursor-pointer transform transition-transform duration-200 hover:scale-105`}
        onClick={() => canceling()}
        whileHover={{ scale: 1.05 }}
        whileTap={{ scale: 0.95 }}
      >
        Відхилити заявку
      </motion.button>

      {showForm === "block" && (
        <motion.div
          className="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
        >
          <motion.div
            className="w-130 p-6 bg-white rounded-2xl shadow-lg flex flex-col items-center"
            initial={{ scale: 0.9, opacity: 0 }}
            animate={{ scale: 1, opacity: 1 }}
            transition={{ duration: 0.3 }}
          >
            <h2 className="text-xl font-semibold text-[#3A3A3A] mb-4 uppercase">
              Якість/Доцільність звернення
            </h2>
            <div className="flex gap-2 mb-6">
              {[1, 2, 3, 4, 5].map((num) => (
                <motion.span
                  key={num}
                  className="text-6xl text-yellow-400 cursor-pointer"
                  whileHover={{ scale: 1.2 }}
                  onMouseEnter={() => {
                    if (selectedStar === 0) setHoveredStar(num);
                  }}
                  onMouseLeave={() => {
                    if (selectedStar === 0) setHoveredStar(0);
                  }}
                  onClick={() => setSelectedStar(num)}
                >
                  {num <= (selectedStar || hoveredStar) ? "★" : "☆"}
                </motion.span>
              ))}
            </div>

            <motion.button
              className="w-full bg-[#FFBE7D] hover:bg-[#ffa94d] text-white font-bold py-2 px-4 rounded-lg"
              onClick={() => {
                sendApplicationUpdate();
                setShowForm("hidden");
              }}
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
            >
              Надіслати
            </motion.button>
          </motion.div>
        </motion.div>
      )}
    </motion.div>
  );
}
