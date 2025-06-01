import { Arrows } from "./arrows";
import Image from "next/image";
import { useEffect, useState } from "react";
import { useRef } from "react";
import { motion } from "framer-motion";
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
      fetch(`https://upcity.live/application/${id}`, {
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
    <motion.div
      className="flex-col w-[35%] h-full px-8 overflow-y-auto bg-[#FBF9F] flex-wrap"
      initial={{ opacity: 0, x: -40 }}
      animate={{ opacity: 1, x: 0 }}
      transition={{ duration: 0.6, ease: "easeOut" }}
    >
      <motion.div
        className="w-full h-auto justify-between flex"
        initial={{ opacity: 0, y: -10 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.1, duration: 0.4 }}
      >
        <h4 className="text-[#3A3A3A] text-xl font-light">
          # {info.application_id}
        </h4>
        <Link href={check ? "https://up-city-8xew.vercel.app/kp_working" : "https://up-city-8xew.vercel.app/appeal"}>
        <Arrows />
        </Link>
      </motion.div>

      <motion.span
        className="text-[#3A3A3A] text-base font-light"
        initial={{ opacity: 0, y: 5 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.2, duration: 0.4 }}
      >
        Назва
      </motion.span>

      <motion.h1
        className="text-[#3A3A3A] text-4xl font-semibold mb-3"
        initial={{ opacity: 0, scale: 0.95 }}
        animate={{ opacity: 1, scale: 1 }}
        transition={{ delay: 0.3, duration: 0.5, ease: "easeOut" }}
      >
        {info.name}
      </motion.h1>

      <motion.span
        className="text-[#3A3A3A] text-base font-light"
        initial={{ opacity: 0, y: 5 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.4, duration: 0.4 }}
      >
        Адреса
      </motion.span>

      <motion.h3
        className="text-[#3A3A3A] font-normal text-xl mb-3"
        initial={{ opacity: 0, y: 5 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.45, duration: 0.4 }}
      >
        {info.address}
      </motion.h3>

      <motion.div
        className="flex gap-8 mb-8"
        initial={{ opacity: 0, y: 10 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.5, duration: 0.4 }}
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
        initial={{ opacity: 0, y: 5 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.55, duration: 0.4 }}
      >
        Користувач
      </motion.span>

      <motion.h3
        className="text-[#3A3A3A] font-normal text-xl mb-3"
        initial={{ opacity: 0, y: 5 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.6, duration: 0.4 }}
      >
        {info.user.name}
      </motion.h3>

      <motion.span
        className="text-[#3A3A3A] text-base font-light"
        initial={{ opacity: 0, y: 5 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.65, duration: 0.4 }}
      >
        КП
      </motion.span>

      <motion.h3
        className="text-[#3A3A3A] font-normal text-xl mb-6"
        initial={{ opacity: 0, y: 5 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.7, duration: 0.4 }}
      >
        КП «{info.utility_company.name}»
      </motion.h3>

      <motion.span
        className="text-[#3A3A3A] text-base font-light"
        initial={{ opacity: 0, y: 5 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.75, duration: 0.4 }}
      >
        Опис
      </motion.span>

      <motion.h3
        className="text-[#3A3A3A] font-normal text-xl mb-6"
        initial={{ opacity: 0, y: 5 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.8, duration: 0.4 }}
      >
        {info.description}
      </motion.h3>

      <motion.div
        className="flex gap-4"
        initial={{ opacity: 0, y: 10 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.85, duration: 0.4 }}
      >
        <div
          className={`${
            info.status === "Виконано" ? "bg-[#EBFFEE]" : "bg-[#EA6464]"
          } w-[30%] h-6 flex rounded-md content-center items-center gap-1 px-1 mb-8`}
        >
          <div
            className={`${
              info.status === "Виконано" ? "bg-[#EBFFEE]" : "bg-[#612A2A]"
            } w-1 h-1 rounded-4xl`}
          ></div>
          <p
            className={`${
              info.status === "Виконано" ? "text-[#589D51]" : "text-[#612A2A]"
            } text-sm font-normal`}
          >
            {info.status}
          </p>
        </div>
        <div className="bg-[#EDEDED] w-22 h-6 flex rounded-md content-center items-center gap-1 px-1">
          <p className="text-[#848484] text-sm font-normal">
            {info.application_date.slice(0, 10)}
          </p>
        </div>
        <div></div>
      </motion.div>

      <motion.span
        className="text-[#3A3A3A] text-base font-normal"
        initial={{ opacity: 0, y: 5 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.9, duration: 0.4 }}
      >
        Фото до
      </motion.span>

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

      

      {info.status === "Відхилено" ? null : (<>
        <motion.span
        className="text-[#3A3A3A] text-base font-normal"
        initial={{ opacity: 0, y: 5 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 1.0, duration: 0.4 }}
      ></motion.span>
       Фото після

      <motion.div
        className="w-full h-[30%] relative mt-[5%] mb-[5%]" 
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.9 }}
      >
        <Image
          src={info.report.image.image_url || "/images/Fon.png"}
          alt="fon"
          fill 
          className="object-cover rounded-4xl" 
          unoptimized
        />
      </motion.div>
      </>)}
       

      <motion.div
        className="w-[100%] bg-[#FBF9F4] p-6 mt-5 flex flex-col items-center"
        initial={{ opacity: 0, y: 10 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 1.1, duration: 0.5 }}
      >
        <h2 className="text-medium font-semibold text-[#3A3A3A] mb-4 uppercase">
          Якість/Доцільність звернення
        </h2>

        {/* ⭐️ Зірки рейтингу (замінено на motion span для анімації) */}
        <div className="flex gap-2 mb-6">
          {[1, 2, 3, 4, 5].map((num) => (
            <motion.span
              key={num}
              className="text-6xl text-yellow-400 cursor-pointer select-none"
              whileHover={{ scale: 1.3, rotate: 10 }}
              whileTap={{ scale: 0.9, rotate: 0 }}
            >
              {num <= (selectedStar || hoveredStar) ? "★" : "☆"}
            </motion.span>
          ))}
        </div>
      </motion.div>
    </motion.div>
  );
}
