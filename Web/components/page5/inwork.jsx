import { useEffect, useState } from "react";
import Image from "next/image";
import Link from "next/link";
import { motion, AnimatePresence } from "framer-motion";

export function Inwork() {
  const [appeal, setAppeals] = useState([]);
  const [appealEnded, setAppealEnded] = useState([]);
  const [appealsWork, setAppealsWork] = useState([]);
  const [isVisible1, setIsVisible1] = useState(true);
  const [isVisible2, setIsVisible2] = useState(true);
  const [try1, setTry1] = useState(true);
  const [try2, setTry2] = useState(true);
  const [isFilterVisible, setIsFilterVisible] = useState(false);
  const [selectedAlphaSort, setSelectedAlphaSort] = useState("");
  const [selectedDate, setSelectedDate] = useState("");
  const [selectedStatus, setSelectedStatus] = useState("");
  const [searchQuery, setSearchQuery] = useState("");

  useEffect(() => {
    const token = sessionStorage.getItem("access_token");

    fetch("https://upcity.live/application/all_by_user", {
      method: "GET",
      headers: {
        Accept: "application/json",
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        setAppeals(data);
      })
      .catch((error) => {
        console.error("❌ Помилка при завантаженні:", error);
      });
  }, []);

  useEffect(() => {
    const inWork = appeal.filter(
      (item) => item.status === "В роботі" || item.status===  "Не розглянута",
    );
    const notInWork = appeal.filter(
      (item) => item.status !== "В роботі" && item.status !== "Не розглянута",
    );

    setAppealsWork(inWork.slice(0, 6));
    setAppealEnded(notInWork.slice(0, 12));
  }, [appeal]);

  const fetchApplications = async () => {
    const token = sessionStorage.getItem("access_token");
    const params = new URLSearchParams();
    if (selectedStatus) params.append("sort_by_status", selectedStatus);
    if (selectedDate) params.append("sort_by_date", selectedDate);
    if (selectedAlphaSort) params.append("sort_by_name", selectedAlphaSort);
    console.log(`смотри https://upcity.live/application/?${params}`);
    try {
      const response = await fetch(
        `https://upcity.live/application/all_by_user/?${params}`,
        {
          headers: {
            Accept: "application/json",
            Authorization: `Bearer ${token}`,
          },
        },
      );

      if (!response.ok) {
        throw new Error(`Помилка: ${response.status}`);
      }

      const data = await response.json();
      setAppeals(data);
      setSelectedAlphaSort("");
      setSelectedDate("");
      setSelectedStatus("");
    } catch (error) {
      console.error("Не вдалося отримати дані:", error);
    }
  };

  const change_status1 = () => {
    if (try1) {
      setIsVisible2(false);
      setTimeout(() => {
        const inWork = appeal.filter(
          (item) => item.status === "В роботі" || item.status === "Не розглянута",
        );
        setAppealsWork(inWork);
        setIsVisible1(true);
        setTry1(false);
      }, 500);
    } else {
      setIsVisible2(true);
      setTimeout(() => {
        const inWork = appeal.filter(
          (item) => item.status === "В роботі" || item.status === "Не розглянута",
        );
        setAppealsWork(inWork.slice(0, 6));
        setIsVisible1(true);
        setTry1(true);
      }, 500);
    }
  };

  const change_status2 = () => {
    if (try2) {
      setIsVisible1(false);
      setTimeout(() => {
        const notInWork = appeal.filter(
          (item) =>
            item.status !== "В роботі" && item.status !== "Не розглянута",
        );
        setAppealEnded(notInWork);
        setIsVisible2(true);
        setTry2(false);
      }, 500);
    } else {
      setIsVisible1(true);
      setTimeout(() => {
        const notInWork = appeal.filter(
          (item) =>
            item.status !== "В роботі" && item.status !== "Не розглянута",
        );
        setAppealEnded(notInWork.slice(0, 12));
        setIsVisible2(true);
        setTry2(true);
      }, 500);
    }
  };

  const containerVariants = {
    hidden: {
      opacity: 0,
      maxHeight: 0,
      overflow: "hidden",
      transition: { duration: 0.5 },
    },
    visible: {
      opacity: 1,
      maxHeight: 1000,
      overflow: "visible",
      transition: { duration: 0.5, ease: "easeInOut" },
    },
  };

 
  const backdropVariants = {
    hidden: { opacity: 0 },
    visible: { opacity: 1, transition: { duration: 0.3 } },
  };

  const modalVariants = {
    hidden: { opacity: 0, y: -50, scale: 0.9 },
    visible: {
      opacity: 1,
      y: 0,
      scale: 1,
      transition: { duration: 0.3, ease: "easeOut" },
    },
  };

  console.log(appeal);
  console.log(appealsWork)
  return (
    <div className="w-full flex flex-col px-12 my-4">
      <div className="w-[35%] mt-40 flex gap-3 items-center pl-10">
        <div className="relative w-[90%]">
          <input
            type="text"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
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
          className="w-10 h-10 bg-[#FFBE7D] hover:bg-[#f7ad5c] transition-all duration-300 rounded-md place-content-center flex cursor-pointer"
          onClick={() => setIsFilterVisible(true)}
        >
          <Image
            src="/images/Filter.svg"
            alt="filter"
            width={22}
            height={26}
            unoptimized
          />
        </button>
      </div>

      <div className="px-12 ]">
        {/* В роботі */}
        <motion.div
          initial="hidden"
          animate={isVisible1 ? "visible" : "hidden"}
          variants={containerVariants}
          className="overflow-hidden w-[100%]"
        >
          <div className="w-full h-auto flex justify-between mt-10">
            <h1 className="text-[#3A3A3A] text-4xl font-semibold">В роботі</h1>
            <button
              className="text-[#BFBBB7] mr-[1%] text-base font-medium uppercase cursor-pointer transition duration-300 ease-in-out hover:text-[#3A3A3A] hover:underline hover:scale-105"
              onClick={change_status1}
            >
              {try1 ? "Переглянути всі" : "Згорнути"}
            </button>
          </div>
          <div className="w-full mt-8 flex gap-4 mb-10 flex-wrap">
            {appealsWork.length < 1 ? (
              <>
                <h1 className="w-[100%] flex justify-center text-4xl mb-[5%]  font-semibold text-orange-300">
                  Покищо тут пусто
                </h1>
              </>
            ) : (
              <>
                {appealsWork
                  .filter((item) =>
                    item.name.toLowerCase().includes(searchQuery.toLowerCase()),
                  )
                  .map((item) => (
                    <Link
                      href={`${
                        item.status === "В роботі"
                          ? `/kp#${item.application_id}`
                          : `/kpWorking#${item.application_id}`
                      }`}
                      key={item.application_id}
                    >
                      <div className="w-60 h-37 bg-white rounded-lg flex flex-col justify-between py-3 px-3 drop-shadow-xl mb-4 cursor-pointer transition-all duration-300 ease-in-out hover:scale-105 hover:shadow-2xl overflow-hidden">
                        <p className="text-[#3A3A3A] text-xs font-light truncate">
                          #{item.application_id}
                        </p>

                        <h2 className="text-[#1E1E1E] text-lg font-semibold mb-2 truncate">
                          {item.name}
                        </h2>

                        <div className="flex w-full px-1 gap-2 mb-2">
                          <div
                            className={`w-fit h-6 ${
                              item.status === "В роботі"
                                ? "bg-[#FBF0E5]"
                                : "bg-[#EDEDED]"
                            } flex items-center px-2 gap-1 rounded-sm`}
                          >
                            <div
                              className={`w-2 h-2 rounded-full ${
                                item.status === "В роботі"
                                  ? "bg-[#957A5E]"
                                  : "bg-[#848484]"
                              }`}
                            ></div>
                            <span
                              className={`${
                                item.status === "В роботі"
                                  ? "text-[#957A5E]"
                                  : "text-[#848484]"
                              } font-normal text-xs`}
                            >
                              {item.status === "В роботі"
                                ? "В роботі"
                                : "Очікує"}
                            </span>
                          </div>

                          <div className="w-fit h-6 bg-[#EDEDED] flex items-center px-2 gap-1 rounded-sm">
                            <span className="text-[#848484] font-normal text-xs">
                              {item.application_date.slice(0, 10)}
                            </span>
                          </div>
                        </div>

                        <span className="text-[#848484] text-xs font-light line-clamp-2">
                          КП «{item.utility_company.name}»
                        </span>
                      </div>
                    </Link>
                  ))}
              </>
            )}
          </div>
        </motion.div>

        {/* Архів */}
        <motion.div
          initial="hidden"
          animate={isVisible2 ? "visible" : "hidden"}
          variants={containerVariants}
          className="overflow-hidden"
        >
          <div className="w-full h-auto flex justify-between">
            <h1 className="text-[#3A3A3A] text-4xl font-semibold mt-10">
              Архів Звернень
            </h1>
          </div>
          <div className="w-[100%] h-auto mt-8 flex flex-wrap gap-4">
            {appealEnded.length < 1 ? (
              <h1 className="w-full flex justify-center text-4xl mb-[5%] font-semibold text-orange-300">
                Поки що тут пусто
              </h1>
            ) : (
              <>
                {appealEnded
                  .filter((item) =>
                    item.name.toLowerCase().includes(searchQuery.toLowerCase()),
                  )
                  .map((item) => (
                    <Link
                      href={`/kpEnded#${item.application_id}`}
                      key={item.application_id}
                    >
                      <div className="w-60 h-37 bg-white rounded-lg flex flex-col justify-between py-3 px-3 drop-shadow-xl mb-4 cursor-pointer transition-all duration-300 ease-in-out hover:scale-105 hover:shadow-2xl overflow-hidden">
                        <p className="text-[#3A3A3A] text-xs font-light">
                          #{item.application_id}
                        </p>
                        <h2 className="text-[#1E1E1E] text-xl mb-[10%] font-semibold  line-clamp-2 leading-snug">
                          {item.name}
                        </h2>
                        <div className="flex w-full h-6 px-1 gap-2 mb-2">
                          <div
                            className={`w-[42%] h-6 ${
                              item.status === "Виконано"
                                ? "bg-[#EBFFEE]"
                                : "bg-[#EA6464]"
                            } flex items-center px-1 gap-1 rounded-sm`}
                          >
                            <div
                              className={`w-2 h-2 rounded-full ${
                                item.status === "Виконано"
                                  ? "bg-[#589D51]"
                                  : "bg-[#814242]"
                              }`}
                            ></div>
                            <span
                              className={`${
                                item.status === "Виконано"
                                  ? "text-[#589D51]"
                                  : "text-[#814242]"
                              } font-normal text-sm   `}
                            >
                              {item.status}
                            </span>
                          </div>
                          <div className="w-[42%] h-6 bg-[#EDEDED] flex items-center px-1 gap-1 rounded-sm">
                            <span className="text-[#848484] font-normal text-sm">
                              {item.application_date.slice(0, 10)}
                            </span>
                          </div>
                        </div>
                        <span className="text-[#848484] text-xs font-light mt-auto">
                          КП «{item.utility_company.name}»
                        </span>
                      </div>
                    </Link>
                  ))}
              </>
            )}
          </div>
          <div className="w-full h-auto flex justify-center mt-5 mb-40">
            <button
              className="text-[#BFBBB7] text-base font-medium uppercase cursor-pointer transition duration-300 ease-in-out hover:text-[#3A3A3A] hover:underline hover:scale-105"
              onClick={change_status2}
            >
              {try2 ? "Переглянути всі" : "Згорнути"}
            </button>
          </div>
        </motion.div>
      </div>

      <AnimatePresence>
        {isFilterVisible && (
          <motion.div
            className="fixed inset-0 z-50 flex justify-center items-center bg-white/10 backdrop-blur-sm"
            initial="hidden"
            animate="visible"
            exit="hidden"
            variants={backdropVariants}
          >
            <motion.div
              className="relative w-full max-w-[600px] bg-white rounded-xl px-10 py-8 shadow-xl"
              initial="hidden"
              animate="visible"
              exit="hidden"
              variants={modalVariants}
            >
              {/* Кнопка закриття */}
              <button
                className="absolute top-4 right-4 cursor-pointer text-3xl text-black font-bold hover:text-gray-500 transition-colors duration-200"
                onClick={() => {
                  setIsFilterVisible(false);
                  setSelectedAlphaSort("");
                  setSelectedDate("");
                  setSelectedStatus("");
                }}
              >
                &times;
              </button>

              {/* Заголовок */}
              <h2 className="text-2xl font-semibold mb-6">Фільтрація</h2>

              {/* Алфавіт */}
              <div className="mb-6">
                <p className="text-sm text-gray-600 mb-2">Алфавіт</p>
                <button
                  className={`block font-medium cursor-pointer mb-1 ${selectedAlphaSort === "А-Я" ? "text-orange-500" : "text-black hover:text-orange-500"}`}
                  onClick={() => setSelectedAlphaSort("А-Я")}
                >
                  А - Я
                </button>
                <button
                  className={`block font-medium cursor-pointer ${selectedAlphaSort === "Я-А" ? "text-orange-500" : "text-black hover:text-orange-500"}`}
                  onClick={() => setSelectedAlphaSort("Я-А")}
                >
                  Я - А
                </button>
              </div>

              {/* Дата */}
              <div className="mb-8">
                <p className="text-sm text-gray-600 mb-2">Датою</p>
                <button
                  className={`block font-medium cursor-pointer mb-1 ${selectedDate === "За зростанням" ? "text-orange-500" : "text-black hover:text-orange-500"}`}
                  onClick={() => setSelectedDate("За зростанням")}
                >
                  За зростанням
                </button>
                <button
                  className={`block font-medium cursor-pointer ${selectedDate === "За спаданням" ? "text-orange-500" : "text-black hover:text-orange-500"}`}
                  onClick={() => setSelectedDate("За спаданням")}
                >
                  За спаданням
                </button>
              </div>

              {/* Статус */}
              <div className="mb-8">
                <p className="text-sm text-gray-600 mb-2">Статусом</p>
                <button
                  className={`block font-medium cursor-pointer mb-1 ${selectedStatus === "В роботі" ? "text-orange-500" : "text-black hover:text-orange-500"}`}
                  onClick={() => setSelectedStatus("В роботі")}
                >
                  В роботі
                </button>
                <button
                  className={`block font-medium cursor-pointer mb-1 ${selectedStatus === "Виконано" ? "text-orange-500" : "text-black hover:text-orange-500"}`}
                  onClick={() => setSelectedStatus("Виконано")}
                >
                  Виконано
                </button>
                <button
                  className={`block font-medium cursor-pointer ${selectedStatus === "Відхилено" ? "text-orange-500" : "text-black hover:text-orange-500"}`}
                  onClick={() => setSelectedStatus("Відхилено")}
                >
                  Відхилено
                </button>
              </div>

              {/* Кнопка застосування */}
              <button
                className="w-full h-12 cursor-pointer bg-orange-400 rounded-md text-white text-sm font-semibold 
        hover:bg-orange-300 active:scale-95 transition-all duration-200 ease-in-out"
                onClick={() => {
                  fetchApplications();
                  setIsFilterVisible(false);
                }}
              >
                ЗАСТОСУВАТИ ФІЛЬТРИ
              </button>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
}
