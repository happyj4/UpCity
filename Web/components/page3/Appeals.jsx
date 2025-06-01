import Image from "next/image";
import { useEffect, useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import Link from "next/link";

export function Appeals() {
  const [applications, setApplications] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [isFilterVisible, setIsFilterVisible] = useState(false);
  const [selectedAlphaSort, setSelectedAlphaSort] = useState("");
  const [selectedDate, setSelectedDate] = useState("");
  const [selectedStatus, setSelectedStatus] = useState("");

  useEffect(() => {
    const fetchApplications = async () => {
      try {
        const response = await fetch("https://upcity.live/application", {
          headers: {
            Accept: "application/json",
          },
        });

        if (!response.ok) {
          console.error("Статус:", response.status);
          throw new Error("Помилка при отриманні даних");
        }

        const data = await response.json();

       
        const filteredData = data.filter(
          (app) => app.status !== "Не розглянута",
        );

        console.log("Відібрані звернення:", filteredData);
        setApplications(filteredData);
      } catch (error) {
        console.error("Помилка запиту:", error);
      }
    };

    fetchApplications();
  }, []);

  const fetchApplications = async () => {
    const params = new URLSearchParams();
    if (selectedStatus) params.append("sort_by_status", selectedStatus);
    if (selectedDate) params.append("sort_by_date", selectedDate);
    if (selectedAlphaSort) params.append("sort_by_name", selectedAlphaSort);
    console.log(`смотри https://upcity.live/application/?${params}`);
    try {
      const response = await fetch(
        `https://upcity.live/application/?${params}`,
        {
          headers: {
            Accept: "application/json",
          },
        },
      );

      if (!response.ok) {
        throw new Error(`Помилка: ${response.status}`);
      }

      const data = await response.json();
      setApplications(data);
      setSelectedAlphaSort("");
      setSelectedDate("");
      setSelectedStatus("");
    } catch (error) {
      console.error("Не вдалося отримати дані:", error);
    }
  };

  const filteredApplications = applications.filter((item) =>
    item.name.toLowerCase().includes(searchQuery.toLowerCase()),
  );


  const containerVariants = {
    hidden: { opacity: 0 },
    visible: {
      opacity: 1,
      transition: {
        staggerChildren: 0.1,
      },
    },
  };

 
  const itemVariants = {
    hidden: { opacity: 0, y: 20 },
    visible: {
      opacity: 1,
      y: 0,
      transition: { duration: 0.4, ease: "easeOut" },
    },
  };

  return (
    <div className="w-[35%] h-screen flex-col pt-30">
      <div className="w-full flex gap-3 items-center">
        <div className="relative ml-[4%] w-[80%]">
          <Image
            className="absolute left-3 top-1/2 transform -translate-y-1/2"
            src="/images/Loop.png"
            alt="loop"
            width={18}
            height={18}
            unoptimized
          />
          <input
            type="text"
            placeholder="Пошук за ім’ям"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="w-full p-3 pl-10 bg-white rounded-xl border border-gray-300 focus:outline-none focus:ring-2 focus:ring-[#FFBE7D] text-sm"
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
      <motion.div
        className="w-[100%] max-h-[80%] overflow-y-auto flex mt-10 bg-[#FBF9F] gap-[7%] flex-wrap justify-center"
        variants={containerVariants}
        initial="hidden"
        animate="visible"
      >
        {filteredApplications.map((item, index) => {
          return (
            <Link
              href={
                item.status === "В роботі"
                  ? `https://up-city-8xew.vercel.app/kp#${item.application_id}`
                  : `https://up-city-8xew.vercel.app/kpEnded#${item.application_id}`
              }
              key={index}
            >
              <motion.div
                className="w-57 h-35 bg-white rounded-lg flex flex-col py-3 px-3 drop-shadow-xl mb-4 transition-all duration-300 ease-in-out hover:scale-105 hover:shadow-2xl cursor-pointer overflow-hidden"
                variants={itemVariants}
              >
                <p className="text-[#3A3A3A] text-xs font-light truncate">
                  #{item.application_id}
                </p>

                <h2 className="text-[#1E1E1E] text-lg font-semibold mb-5 truncate">
                  {item.name}
                </h2>

                <div className="flex w-full px-1 gap-2 mb-2">
                  <div
                    className={`w-fit h-6 ${
                      item.status === "В роботі"
                        ? "bg-[#FBF0E5]"
                        : item.status === "Виконано"
                          ? "bg-[#EBFFEE]"
                          : "bg-[#EA6464]"
                    } flex items-center px-2 gap-1 rounded-sm`}
                  >
                    <div
                      className={`w-2 h-2 rounded-full ${
                        item.status === "В роботі"
                          ? "bg-[#957A5E]"
                          : item.status === "Виконано"
                            ? "bg-[#589D51]"
                            : "bg-[#612A2A]"
                      }`}
                    ></div>
                    <span
                      className={`${
                        item.status === "В роботі"
                          ? "text-[#957A5E]"
                          : item.status === "Виконано"
                            ? "text-[#589D51]"
                            : "text-[#612A2A]"
                      } font-normal text-xs`}
                    >
                      {item.status}
                    </span>
                  </div>

                  <div className="w-fit h-6 bg-[#EDEDED] flex items-center px-2 rounded-sm">
                    <span className="text-[#848484] font-normal text-xs">
                      {item.application_date.split("T")[0]}
                    </span>
                  </div>
                </div>

                <span className="text-[#848484] text-xs font-light line-clamp-2">
                  КП"{item.utility_company.name}"
                </span>
              </motion.div>
            </Link>
          );
        })}
      </motion.div>

      <AnimatePresence>
        {isFilterVisible && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            className="fixed inset-0 bg-white/30 backdrop-blur-sm z-50 flex justify-center items-center"
          >
            <motion.div
              initial={{ scale: 0.95, opacity: 0 }}
              animate={{ scale: 1, opacity: 1 }}
              exit={{ scale: 0.95, opacity: 0 }}
              transition={{ duration: 0.2, ease: "easeInOut" }}
              className="relative w-full max-w-[600px] bg-white rounded-xl px-10 py-8"
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
                  className={`block font-medium cursor-pointer mb-1 ${
                    selectedAlphaSort === "А-Я"
                      ? "text-orange-500"
                      : "text-black hover:text-orange-500"
                  }`}
                  onClick={() => setSelectedAlphaSort("А-Я")}
                >
                  А - Я
                </button>
                <button
                  className={`block font-medium cursor-pointer ${
                    selectedAlphaSort === "Я-А"
                      ? "text-orange-500"
                      : "text-black hover:text-orange-500"
                  }`}
                  onClick={() => setSelectedAlphaSort("Я-А")}
                >
                  Я - А
                </button>
              </div>

              {/* Дата */}
              <div className="mb-8">
                <p className="text-sm text-gray-600 mb-2">Датою</p>
                <button
                  className={`block font-medium cursor-pointer mb-1 ${
                    selectedDate === "За зростанням"
                      ? "text-orange-500"
                      : "text-black hover:text-orange-500"
                  }`}
                  onClick={() => setSelectedDate("За зростанням")}
                >
                  За зростанням
                </button>
                <button
                  className={`block font-medium cursor-pointer ${
                    selectedDate === "За спаданням"
                      ? "text-orange-500"
                      : "text-black hover:text-orange-500"
                  }`}
                  onClick={() => setSelectedDate("За спаданням")}
                >
                  За спаданням
                </button>
              </div>

              {/* Статус */}
              <div className="mb-8">
                <p className="text-sm text-gray-600 mb-2">Статусом</p>
                <button
                  className={`block font-medium cursor-pointer mb-1 ${
                    selectedStatus === "В роботі"
                      ? "text-orange-500"
                      : "text-black hover:text-orange-500"
                  }`}
                  onClick={() => setSelectedStatus("В роботі")}
                >
                  В роботі
                </button>
                <button
                  className={`block font-medium cursor-pointer ${
                    selectedStatus === "Виконано"
                      ? "text-orange-500"
                      : "text-black hover:text-orange-500"
                  }`}
                  onClick={() => setSelectedStatus("Виконано")}
                >
                  Виконано
                </button>
                <button
                  className={`block font-medium cursor-pointer ${
                    selectedStatus === "Відхилено"
                      ? "text-orange-500"
                      : "text-black hover:text-orange-500"
                  }`}
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
