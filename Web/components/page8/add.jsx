import { useState } from "react";

export function Add() {
  const [formData, setFormData] = useState({
    name: "",
    address: "",
    phone: "",
    email: "",
    password: "",
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("access_token");

    try {
      const response = await fetch("http://46.101.245.42/utility_company/", {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        setFormData({
          name: "",
          address: "",
          phone: "",
          email: "",
          password: "",
        });
        window.location.href = "http://localhost:3000/list_kp";
      } else {
        const errorData = await response.json();
        alert("Помилка: " + JSON.stringify(errorData));
      }
    } catch (error) {
      alert("Мережева помилка: " + error.message);
    }
  };

  return (
    <div className="w-full h-auto pt-20">
      <h1 className="text-[#3A3A3A] text-4xl font-semibold mt-15 mb-15 pl-25">
        Додавання комунального підприємства
      </h1>
      <div className="flex justify-center w-full h-auto">
        <form
          onSubmit={handleSubmit}
          className="felxflex-col h-auto w-[25%]"
        >
          <span className="text-[#3A3A3A] text-base font-light">
            Назва комунального підприємства
          </span>
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            placeholder="Введіть назву"
            className="w-full h-16  rounded-lg  bg-[#FFFEFC] focus:bg-white focus:border-orange-400 focus:ring-2 focus:ring-orange-200 outline-none drop-shadow-md mt-2 mb-6 pl-6 transition-all duration-300"
          />

          <span className="text-[#3A3A3A] text-base font-light">
            Адреса комунального підприємства
          </span>
          <input
            type="text"
            name="address"
            value={formData.address}
            onChange={handleChange}
            placeholder="Введіть адресу"
            className="w-full h-16  rounded-lg  bg-[#FFFEFC] focus:bg-white focus:border-orange-400 focus:ring-2 focus:ring-orange-200 outline-none drop-shadow-md mt-2 mb-6 pl-6 transition-all duration-300"
          />

          <span className="text-[#3A3A3A] text-base font-light">
            Номер телефону комунального підприємства
          </span>
          <input
            type="text"
            name="phone"
            value={formData.phone}
            onChange={handleChange}
            placeholder="+380..."
            className="w-full h-16  rounded-lg  bg-[#FFFEFC] focus:bg-white focus:border-orange-400 focus:ring-2 focus:ring-orange-200 outline-none drop-shadow-md mt-2 mb-6 pl-6 transition-all duration-300"
          />

          <span className="text-[#3A3A3A] text-base font-light">
            Електронна пошта комунального підприємства
          </span>
          <input
            type="text"
            name="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="email@example.com"
            className="w-full h-16  rounded-lg  bg-[#FFFEFC] focus:bg-white focus:border-orange-400 focus:ring-2 focus:ring-orange-200 outline-none drop-shadow-md mt-2 mb-6 pl-6 transition-all duration-300"
          />

          <span className="text-[#3A3A3A] text-base font-light">
            Пароль комунального підприємства
          </span>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            placeholder="******"
            className="w-full h-16  rounded-lg  bg-[#FFFEFC] focus:bg-white focus:border-orange-400 focus:ring-2 focus:ring-orange-200 outline-none drop-shadow-md mt-2 mb-6 pl-6 transition-all duration-300"
          />

          <button
            type="submit"
            className="w-full mb-30 cursor-pointer h-14 bg-[rgba(255,190,125,0.8)] hover:bg-orange-400 transition-all duration-300 transform hover:scale-105 uppercase rounded-lg text-white text-xl font-semibold shadow-md hover:shadow-2xl"
          >
            зберегти
          </button>
        </form>
      </div>
    </div>
  );
}
