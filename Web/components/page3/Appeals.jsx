import Image from "next/image";
import { useEffect, useState } from "react";
import { motion } from "framer-motion";

export function Appeals() {
  const [applications, setApplications] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");

  useEffect(() => {
    const fetchApplications = async () => {
      try {
        const response = await fetch("http://46.101.245.42/application", {
          headers: {
            Accept: "application/json",
          },
        });

        if (!response.ok) {
          console.error("Статус:", response.status);
          throw new Error("Помилка при отриманні даних");
        }

        const data = await response.json();
        console.log("Звернення:", data);
        setApplications(data);
      } catch (error) {
        console.error("Помилка запиту:", error);
      }
    };

    fetchApplications();
  }, []);

  const filteredApplications = applications.filter((item) =>
    item.name.toLowerCase().includes(searchQuery.toLowerCase())
  );

  // Варіанти анімації для контейнера (стакінг)
  const containerVariants = {
    hidden: { opacity: 0 },
    visible: {
      opacity: 1,
      transition: {
        staggerChildren: 0.1,
      },
    },
  };

  // Варіанти анімації для кожного звернення
  const itemVariants = {
    hidden: { opacity: 0, y: 20 },
    visible: { opacity: 1, y: 0, transition: { duration: 0.4, ease: "easeOut" } },
  };

  return (
    <div className="w-[35%] h-screen flex-col pt-30">
      <div className="w-full flex gap-3 items-center">
        <div className="relative w-[85%]">
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
        <button className="w-10 h-10 bg-[#FFBE7D] hover:bg-[#f7ad5c] transition-all duration-300 rounded-md place-content-center flex cursor-pointer">
          <Image
            src="/images/Filter.svg"
            alt="filter"
            width={22}
            height={26}
            unoptimized
          />
        </button>
      </div>
      <p className="mt-4 text-[#3A3A3A] text-sm font-normal">
        Звернення{" "}
        <span className="text-[#896B4E] underline cursor-pointer">
          за останній місяць
        </span>
      </p>
      <motion.div
        className="w-full max-h-[400px] overflow-y-auto flex mt-10 bg-[#FBF9F] gap-8 flex-wrap"
        variants={containerVariants}
        initial="hidden"
        animate="visible"
      >
        {filteredApplications.map((item, index) => {
          return (
            <motion.div
              key={index}
              className="w-57 h-35 bg-white rounded-lg flex-col py-2 px-2 drop-shadow-xl mb-4 transition-all duration-300 ease-in-out hover:scale-105 hover:shadow-2xl"
              variants={itemVariants}
            >
              <p className="text-[#3A3A3A] text-xs font-light">#1-2634</p>
              <h2 className="text-[#1E1E1E] text-2xl font-semibold mb-5">
                {item.name}
              </h2>
              <div className="flex w-full h-auto px-2 gap-2 mb-2">
                <div
                  className={`w-20 h-6 ${
                    item.status == "В роботі" ? "bg-[#FBF0E5]" : "bg-[#EBFFEE]"
                  } flex items-center px-1 gap-1 rounded-sm`}
                >
                  <div
                    className={`w-2 h-2 rounded-4xl ${
                      item.status == "В роботі"
                        ? "bg-[#957A5E]"
                        : "bg-[#589D51]"
                    }`}
                  ></div>
                  <span
                    className={`${
                      item.status == "В роботі"
                        ? "text-[#957A5E]"
                        : "text-[#589D51]"
                    } font-normal text-sm`}
                  >
                    {item.status}
                  </span>
                </div>
                <div className="w-20 h-6 bg-[#EDEDED] flex items-center px-1 gap-1 rounded-sm">
                  <span className="text-[#848484] font-normal text-sm">
                    {item.application_date.split("T")[0]}
                  </span>
                </div>
              </div>
              <span className="text-[#848484] text-xs font-light">
                КП"{item.utility_company.name}"
              </span>
            </motion.div>
          );
        })}
      </motion.div>
    </div>
  );
}
