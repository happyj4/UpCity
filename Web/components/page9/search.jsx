import Image from "next/image";
import { Strike } from "./strike";
import { useEffect, useState } from "react";

export function Search() {
  const [people, setPeople] = useState([]);
  const [searchTerm, setSearchTerm] = useState(""); // 🔍 Стан для пошуку

  useEffect(() => {
    const token = localStorage.getItem("access_token");

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

  function deletion(id, index) {
    const token = localStorage.getItem("access_token");

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
        console.log("✅ Користувач успішно заблокований!");
        setPeople((prev) => prev.filter((_, i) => i !== index));
      })
      .catch((error) => {
        console.error("❌ Запит на блокування не вдалось:", error);
      });
  }

  // 🔍 Фільтруємо людей за іменем (регістр незалежний)
  const filteredPeople = people.filter((person) =>
    person.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  console.log(people)

  return (
    <div className="flex flex-col pt-40 px-25 py-6 ">
      <h1 className="text-[#3A3A3A] text-4xl font-semibold">Список громадян</h1>

      <div className="w-full h-auto flex mt-12 gap-4 relative">
        <div className="relative w-[95%]">
          <input
            type="text"
            value={searchTerm} // 🔄 зв'язуємо з searchTerm
            onChange={(e) => setSearchTerm(e.target.value)} // 📝 обробка змін
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
        <button
          className="w-13 h-13 rounded-md drop-shadow-md bg-[#FFBE7D] flex justify-center items-center 
                             transition-all duration-300 hover:bg-[#FFA94D] hover:shadow-lg hover:scale-105 cursor-pointer"
        >
          <Image
            src="/images/Filter.svg"
            alt="filter"
            width={23}
            height={26}
            unoptimized
          />
        </button>
      </div>

      {filteredPeople.map((item, index) => (
        <div
          key={index}
          className="w-full h-47 bg-[#FFFEFC] mt-8 mb-8 flex rounded-md drop-shadow-lg px-6 py-2 items-center 
                transition-all duration-300 transform hover:scale-[1.02] hover:shadow-2xl hover:bg-[#FFF9F3]"
        >
          <div className="w-30 flex justify-center items-center">
            <Image
              src="/images/Avatar2.png"
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
              Рейтинг / <span className="text-3xl font-semibold">{item.rating}</span>
            </p>

            <button
              className="h-10 w-33 cursor-pointer bg-[#DF4720] hover:bg-[#c43818] transition-all duration-300 rounded-xl text-[#FFF] text-xl font-semibold mb-6"
              onClick={() => deletion(item.user_id, index)}
            >
              {(item.blocking)? "Заблоковано": "Заблокувати"}
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
        </div>
      ))}
    </div>
  );
}
