import { useEffect, useState } from "react";
import Image from "next/image";
import Link from "next/link";
import { useRouter } from "next/router";
import { motion } from "framer-motion";
import Swal from "sweetalert2";

export function List() {
  const [listi, setListi] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [isFilterVisible, setIsFilterVisible] = useState(false);
  const [selectedAlphaSort, setSelectedAlphaSort] = useState("");
  const [selectedRatingSort, setSelectedRatingSort] = useState("");
  const router = useRouter();

  useEffect(() => {
    const token = sessionStorage.getItem("access_token");

    fetch("http://46.101.245.42/utility_company/", {
      method: "GET",
      headers: {
        Accept: "application/json",
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        setListi(data);
        console.log(data);
      })
      .catch((error) => {
        console.error("Помилка при отриманні даних:", error);
      });
  }, []);

  function edit(id) {
    router.push(`/edit_kp#${id}`);
  }

  function deletion(id, index) {
    const token = sessionStorage.getItem("access_token");

    Swal.fire({
      title: "Ви впевнені?",
      text: "Цей запис буде видалено безповоротно!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#3085d6",
      confirmButtonText: "Так, видалити!",
      cancelButtonText: "Скасувати",
    }).then((result) => {
      if (result.isConfirmed) {
        fetch(`http://46.101.245.42/utility_company/${id}`, {
          method: "DELETE",
          headers: {
            Accept: "*/*",
            Authorization: `Bearer ${token}`,
          },
        })
          .then((res) => {
            if (!res.ok) {
              throw new Error("Помилка при видаленні");
            }
            setListi((prev) => prev.filter((_, i) => i !== index));
            Swal.fire("Видалено!", "Запис було успішно видалено.", "success");
          })
          .catch((err) => {
            console.error("Помилка:", err);
            Swal.fire("Помилка", "Не вдалося видалити запис", "error");
          });
      }
    });
  }

  const filteredList = listi.filter((item) => {
    const ratingStr = String(item.name);
    return ratingStr.startsWith(searchTerm.trim());
  });

  return (
    <div>
      <div className="flex gap-4 w-full h-auto px-24">
        <div className="relative w-[90%]">
          <input
            type="text"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="w-full h-12 pl-12 pr-4 bg-white rounded-2xl border border-gray-200 shadow-sm focus:outline-none focus:ring-4 focus:ring-[#FFBE7D]/50 focus:border-[#FFBE7D] transition-all duration-300 hover:shadow-md text-sm text-gray-700 placeholder-gray-400"
            placeholder="Пошук за назвою ..."
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
          className="w-[52px] h-[52px] bg-[#FFBE7D] drop-shadow-lg rounded-md flex items-center justify-center 
    transition-all duration-200 ease-in-out hover:bg-[#ffcc97] hover:scale-105 cursor-pointer"
          onClick={() => setIsFilterVisible(true)} // показати фільтр
        >
          <Image
            src="/images/Filter.svg"
            alt="filter"
            width={23}
            height={26}
            unoptimized
          />
        </button>
        <Link href="http://localhost:3000/add_kp">
          <button
            className="w-[52px] h-[52px] bg-[#FFBE7D] drop-shadow-lg rounded-md flex items-center justify-center 
            transition-all duration-200 ease-in-out hover:bg-[#ffcc97] hover:scale-105 cursor-pointer"
          >
            <Image
              src="/images/plusIcon.svg"
              alt="plus"
              width={22}
              height={23}
              unoptimized
            />
          </button>
        </Link>
      </div>

      <div className="w-full h-auto flex flex-wrap mt-8 px-[120px] gap-[96px] mb-[200px]">
        {filteredList.map((item, index) => (
          <motion.div
            key={index}
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.4, delay: index * 0.05 }}
            className="w-[588px] h-[296px] bg-[#FFFEFC] px-6 py-4 rounded-lg drop-shadow-lg transition-all duration-300 ease-in-out transform hover:scale-[1.02] hover:shadow-xl cursor-pointer"
          >
            <div className="flex h-auto justify-between items-center">
              <div className="w-[80%] h-auto break-words">
                <h2 className="text-[#1E1E1E] text-2xl font-semibold">
                  {item.name}
                </h2>
              </div>
              <div className="h-auto">
                <h3 className="text-[#000] text-base font-light">
                  Рейтинг /
                  <span className="text-2xl font-semibold"> {item.rating}</span>
                </h3>
              </div>
            </div>
            <div className="h-auto w-full flex-col mt-8">
              <p className="text-[#1E1E1E] text-xl font-light mb-2">
                61037, м. Харків,
              </p>
              <p className="text-[#1E1E1E] text-xl font-light mb-2">
                {item.address}
              </p>
              <p className="text-[#1E1E1E] text-xl font-light mb-2">
                {item.phone}
              </p>
            </div>
            <div className="flex gap-4 justify-end mt-4">
              <button
                className="w-[136px] h-[44px] bg-[#D9D9D9] rounded-lg text-[#FFF] text-xl font-semibold flex items-center justify-center 
                transition-all duration-200 ease-in-out hover:bg-[#c0c0c0] hover:scale-105 cursor-pointer"
                onClick={() => edit(item.ut_company_id)}
              >
                Редагувати
              </button>

              <button
                className="w-[116px] h-[44px] bg-[#DF4720] rounded-lg text-[#FFF] text-xl font-semibold flex items-center justify-center 
                transition-all duration-200 ease-in-out hover:bg-[#bb3b1a] hover:scale-105 cursor-pointer"
                onClick={() => deletion(item.ut_company_id, index)}
              >
                Видалити
              </button>
            </div>
          </motion.div>
        ))}
      </div>
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
                  selectedAlphaSort === "asc"
                    ? "text-orange-500"
                    : "text-black hover:text-orange-500"
                }`}
                onClick={() => setSelectedAlphaSort("asc")}
              >
                А - Я
              </button>

              <button
                className={`block font-medium cursor-pointer ${
                  selectedAlphaSort === "desc"
                    ? "text-orange-500"
                    : "text-black hover:text-orange-500"
                }`}
                onClick={() => setSelectedAlphaSort("desc")}
              >
                Я - А
              </button>
            </div>

            {/* Рейтинг */}
            <div className="mb-8">
              <p className="text-sm text-gray-600 mb-2">Рейтинг</p>

              <button
                className={`block font-medium cursor-pointer mb-1 ${
                  selectedRatingSort === "asc"
                    ? "text-orange-500"
                    : "text-black hover:text-orange-500"
                }`}
                onClick={() => setSelectedRatingSort("asc")}
              >
                За зростанням
              </button>

              <button
                className={`block font-medium cursor-pointer ${
                  selectedRatingSort === "desc"
                    ? "text-orange-500"
                    : "text-black hover:text-orange-500"
                }`}
                onClick={() => setSelectedRatingSort("desc")}
              >
                За спаданням
              </button>
            </div>

            {/* Кнопка застосування */}
            <button
              className="w-full h-12 cursor-pointer bg-orange-400 rounded-md text-white text-sm font-semibold 
             hover:bg-orange-300 active:scale-95 transition-all duration-200 ease-in-out"
              onClick={() => {
                setIsFilterVisible(false);
                setSelectedAlphaSort("");
                setSelectedRatingSort("");
              }}
            >
              ЗАСТОСУВАТИ ФІЛЬТРИ
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
