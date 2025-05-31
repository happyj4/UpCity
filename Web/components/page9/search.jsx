import Image from "next/image";
import { Strike } from "./strike";
import { useEffect, useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import Swal from "sweetalert2";

export function Search() {
  const [people, setPeople] = useState([]);
  const [searchTerm, setSearchTerm] = useState(""); // 🔍 Стан для пошуку
  const [isFilterVisible, setIsFilterVisible] = useState(false);
  const [selectedAlphaSort, setSelectedAlphaSort] = useState("");
  const [selectedRatingSort, setSelectedRatingSort] = useState("");
  const [selectedSubscribe, setSelectedSubscribe] = useState("");
  const [selectedBlock, setSelectedBlock] = useState("");

  useEffect(() => {
    const token = sessionStorage.getItem("access_token");

    const fetchUsers = async () => {
      try {
        const response = await fetch("http://46.101.245.42/user", {
          headers: {
            Accept: "application/json",
            Authorization: `Bearer ${token}`,
          },
        });

        if (!response.ok) {
          console.error("Статус:", response.status);
          throw new Error("Помилка при отриманні користувачів");
        }

        const data = await response.json();
        setPeople(data);
      } catch (error) {
        console.error("Помилка запиту:", error);
      }
    };

    fetchUsers();
  }, []);

  const fetchApplications = async () => {
    const token = sessionStorage.getItem("access_token");
    const params = new URLSearchParams();
    if (selectedAlphaSort) params.append("sort_by_name", selectedAlphaSort);
    if (selectedRatingSort) params.append("sort_by_rating", selectedRatingSort);
    if (selectedSubscribe)
      params.append("sort_by_subscription", selectedSubscribe);
    if (selectedBlock) params.append("sort_by_blocking", selectedBlock);
    console.log(`смотри http://46.101.245.42/application/?${params}`);
    try {
      const response = await fetch(`http://46.101.245.42/user/?${params}`, {
        headers: {
          Accept: "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error(`Помилка: ${response.status}`);
      }

      const data = await response.json();
      setPeople(data);
      setSelectedAlphaSort("");
      setSelectedRatingSort("");
      setSelectedSubscribe("");
      setSelectedBlock("");
    } catch (error) {
      console.error("Не вдалося отримати дані:", error);
    }
  };

  function deletion(id, index) {
    const token = sessionStorage.getItem("access_token");

    fetch("http://46.101.245.42/user/block/", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        user_id: id,
        reason: "Нарушення правил",
      }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error(`Помилка при блокуванні: ${response.status}`);
        }
        setPeople((prevPeople) =>
          prevPeople.map((person, i) =>
            i === index ? { ...person, blocking: true } : person,
          ),
        );
      })
      .catch((error) => {
        Swal.fire("Запит наблокування не вдався", "Спробуйте ще раз", "error");
      });
  }

  // 🔍 Фільтруємо людей за іменем (регістр незалежний)
  const filteredPeople = people.filter((person) =>
    person.name.toLowerCase().includes(searchTerm.toLowerCase()),
  );

  console.log(people);

  return (
    <motion.div
      className="flex flex-col pt-40 px-25 py-6"
      initial={{ opacity: 0, y: 50 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.6, ease: "easeOut" }}
    >
      <motion.h1
        className="text-[#3A3A3A] text-4xl font-semibold"
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.3, duration: 0.5 }}
      >
        Список громадян
      </motion.h1>

      <motion.div
        className="w-full h-auto flex mt-12 gap-4 relative"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.5, duration: 0.5 }}
      >
        {/* Поле пошуку */}
        <div className="relative w-[95%]">
          <input
            type="text"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="w-full h-12 pl-12 pr-4 bg-white rounded-2xl border border-gray-200 shadow-sm focus:outline-none focus:ring-4 focus:ring-[#FFBE7D]/50 focus:border-[#FFBE7D] transition-all duration-300 hover:shadow-md text-sm text-gray-700 placeholder-gray-400"
            placeholder="Пошук за імʼям..."
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

        {/* Кнопка фільтра */}
        <button
          className="w-13 h-13 rounded-md drop-shadow-md bg-[#FFBE7D] flex justify-center items-center 
                   transition-all duration-300 hover:bg-[#FFA94D] hover:shadow-lg hover:scale-105 cursor-pointer"
          onClick={() => setIsFilterVisible(true)}
        >
          <Image
            src="/images/Filter.svg"
            alt="filter"
            width={23}
            height={26}
            unoptimized
          />
        </button>
      </motion.div>

      {/* Картки користувачів */}
      <AnimatePresence>
        {filteredPeople.map((item, index) => (
          <motion.div
            key={item.user_id}
            className="w-full h-47 bg-[#FFFEFC] mt-8 mb-8 flex rounded-md drop-shadow-lg px-6 py-2 items-center 
                 transition-all duration-300 transform hover:scale-[1.02] hover:shadow-2xl hover:bg-[#FFF9F3]"
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: -10 }}
            transition={{ delay: index * 0.05, duration: 0.4 }}
          >
            <div className="w-30 flex justify-center items-center">
              <Image
                src={item.image ? item.image.image_url : null}
                alt="Avatar2"
                width={90}
                height={90}
                unoptimized
              />
            </div>

            <div className="h-auto w-auto flex flex-col items-start justify-center text-[#000] text-xl font-light ml-6">
              <p>{item.name}</p>
              <p className="text-base text-gray-500">{item.email}</p>
            </div>

            <div className="flex flex-col justify-end items-end ml-auto">
              <p className="text-[#000] text-base font-light mb-6">
                Рейтинг /{" "}
                <span className="text-3xl font-semibold">{item.rating}</span>
              </p>

              <button
                className="h-10 w-33 cursor-pointer bg-[#DF4720] hover:bg-[#c43818] transition-all duration-300 rounded-xl text-[#FFF] text-xl font-semibold mb-6"
                onClick={() => deletion(item.user_id, index)}
              >
                {item.blocking ? "Заблоковано" : "Заблокувати"}
              </button>

              <div className="flex justify-end text-[#000] text-base font-light gap-2 items-center">
                підписка
                <button
                  className={`w-8 h-8 ${item.subscription} rounded-xl flex justify-center items-center`}
                >
                  <Strike />
                </button>
              </div>
            </div>
          </motion.div>
        ))}
        {isFilterVisible && (
          <div className="fixed inset-0 bg-opacity-40 z-50 flex justify-center items-center">
            <div className="relative w-full max-w-[600px] bg-white rounded-xl px-10 py-8">
              {/* Кнопка закриття */}
              <button
                className="absolute top-4 right-4 cursor-pointer text-3xl text-black font-bold hover:text-gray-500 transition-colors duration-200"
                onClick={() => {
                  setIsFilterVisible(false);
                  setSelectedAlphaSort("");
                  setSelectedRatingSort("");
                  setSelectedSubscribe("");
                  setSelectedBlock("");
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

              {/* Рейтинг */}
              <div className="mb-8">
                <p className="text-sm text-gray-600 mb-2">Рейтинг</p>

                <button
                  className={`block font-medium cursor-pointer mb-1 ${
                    selectedRatingSort === "За зростанням"
                      ? "text-orange-500"
                      : "text-black hover:text-orange-500"
                  }`}
                  onClick={() => setSelectedRatingSort("За зростанням")}
                >
                  За зростанням
                </button>

                <button
                  className={`block font-medium cursor-pointer ${
                    selectedRatingSort === "За спаданням"
                      ? "text-orange-500"
                      : "text-black hover:text-orange-500"
                  }`}
                  onClick={() => setSelectedRatingSort("За спаданням")}
                >
                  За спаданням
                </button>
              </div>

              <div className="mb-8">
                <p className="text-sm text-gray-600 mb-2">Підписка</p>

                <button
                  className={`block font-medium cursor-pointer mb-1 ${
                    selectedSubscribe === "З підпискою"
                      ? "text-orange-500"
                      : "text-black hover:text-orange-500"
                  }`}
                  onClick={() => setSelectedSubscribe("З підпискою")}
                >
                  З підпискою
                </button>

                <button
                  className={`block font-medium cursor-pointer ${
                    selectedSubscribe === "Без підписки"
                      ? "text-orange-500"
                      : "text-black hover:text-orange-500"
                  }`}
                  onClick={() => setSelectedSubscribe("Без підписки")}
                >
                  Без підписки
                </button>
                <button
                  className={`block font-medium cursor-pointer ${
                    selectedSubscribe === "Просрочено"
                      ? "text-orange-500"
                      : "text-black hover:text-orange-500"
                  }`}
                  onClick={() => setSelectedSubscribe("Просрочено")}
                >
                  Просрочено
                </button>
              </div>

              <div className="mb-8">
                <p className="text-sm text-gray-600 mb-2">Блокування</p>

                <button
                  className={`block font-medium cursor-pointer mb-1 ${
                    selectedBlock === "Заблоковані"
                      ? "text-orange-500"
                      : "text-black hover:text-orange-500"
                  }`}
                  onClick={() => setSelectedBlock("Заблоковані")}
                >
                  Заблоковані
                </button>

                <button
                  className={`block font-medium cursor-pointer ${
                    selectedBlock === "Не заблоковані"
                      ? "text-orange-500"
                      : "text-black hover:text-orange-500"
                  }`}
                  onClick={() => setSelectedBlock("Не заблоковані")}
                >
                  Не заблоковані
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
            </div>
          </div>
        )}
      </AnimatePresence>
    </motion.div>
  );
}
