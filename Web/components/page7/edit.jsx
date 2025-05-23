import { useEffect, useState } from "react";

export function Edit() {
  const [data, setData] = useState({
    name: "",
    address: "",
    phone: "",
    email: "",
  });
  const hashId = window.location.hash.slice(1);

  // Отримуємо ID з хешу URL
  useEffect(() => {
    const token = localStorage.getItem("access_token");

    if (hashId) {
      fetch(`http://46.101.245.42/utility_company/${hashId}`, {
        method: "GET",
        headers: {
          Accept: "application/json",
          Authorization: `Bearer ${token}`,
        },
      })
        .then((res) => res.json())
        .then((resData) => {
          setData(resData);
        })
        .catch((err) => console.error("Помилка при завантаженні даних:", err));
    }
  }, []);

  // Функція для зміни значень полів
  const handleChange = (e) => {
    setData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async () => {
    const token = localStorage.getItem("access_token");

    try {
      const response = await fetch(
        `http://46.101.245.42/utility_company/${hashId}`,
        {
          method: "PUT",
          headers: {
            Accept: "application/json",
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify(data),
        },
      );

      if (!response.ok) {
        throw new Error("Помилка при збереженні даних");
      }

      const result = await response.json();
      console.log("Успішно оновлено:", result);
      window.location.href = "http://localhost:3000/list_kp";
    } catch (error) {
      console.error("Помилка:", error);
      alert("Не вдалося зберегти дані");
    }
  };

  return (
    <div className="w-full h-auto">
      <h1 className="text-[#3A3A3A] text-4xl font-semibold pt-40 mb-15 pl-35">
        Редактор комунального підприємства
      </h1>
      <div className="flex justify-center w-full h-auto">
        <div className="felxflex-col h-auto w-[25%]">
          <span className="text-[#3A3A3A] text-base font-light">
            Назва комунального підприємства
          </span>
          <input
            type="text"
            name="name"
            value={data.name}
            onChange={handleChange}
            placeholder="Введіть назву"
            className="w-full h-16 rounded-lg bg-[#FFFEFC] focus:bg-white focus:border-orange-400 focus:ring-2 focus:ring-orange-200 outline-none drop-shadow-md mt-2 mb-6 pl-6 transition-all duration-300"
          />

          <span className="text-[#3A3A3A] text-base font-light">
            Адреса комунального підприємства
          </span>
          <input
            type="text"
            name="address"
            value={data.address}
            onChange={handleChange}
            placeholder="Введіть адресу"
            className="w-full h-16 rounded-lg bg-[#FFFEFC] focus:bg-white focus:border-orange-400 focus:ring-2 focus:ring-orange-200 outline-none drop-shadow-md mt-2 mb-6 pl-6 transition-all duration-300"
          />

          <span className="text-[#3A3A3A] text-base font-light">
            Номер телефону комунального підприємства
          </span>
          <input
            type="text"
            name="phone"
            value={data.phone}
            onChange={handleChange}
            placeholder="+380..."
            className="w-full h-16 rounded-lg bg-[#FFFEFC] focus:bg-white focus:border-orange-400 focus:ring-2 focus:ring-orange-200 outline-none drop-shadow-md mt-2 mb-6 pl-6 transition-all duration-300"
          />

          <span className="text-[#3A3A3A] text-base font-light">
            Електронна пошта комунального підприємства
          </span>
          <input
            type="text"
            name="email"
            value={data.email}
            onChange={handleChange}
            placeholder="email@example.com"
            className="w-full h-16 rounded-lg bg-[#FFFEFC] focus:bg-white focus:border-orange-400 focus:ring-2 focus:ring-orange-200 outline-none drop-shadow-md mt-2 mb-6 pl-6 transition-all duration-300"
          />

          <button
            onClick={handleSubmit}
            className="w-full mb-30 cursor-pointer h-14 bg-[rgba(255,190,125,0.8)] hover:bg-orange-400 transition-all duration-300 transform hover:scale-105 uppercase rounded-lg text-white text-xl font-semibold shadow-md hover:shadow-2xl"
          >
            зберегти
          </button>
        </div>
      </div>
    </div>
  );
}
