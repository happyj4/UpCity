import { Header } from "../components/WebComponents/header.jsx";
import React, { useState } from "react";
import { motion } from "framer-motion";
import Swal from "sweetalert2";

export default function RegistrationPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [emailValid, setEmailValid] = useState(true);
  const [passwordValid, setPasswordValid] = useState(true);

  const validateEmail = (value) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    setEmailValid(emailRegex.test(value));
    setUsername(value);
  };

  const validatePassword = (value) => {
    setPasswordValid(value.length >= 4); // Приклад: мінімум 5 символів
    setPassword(value);
  };

  const logIn = async (e) => {
    e.preventDefault();
    if (!emailValid || !passwordValid) {
      Swal.fire(
        "Неправильний формат",
        "Будь ласка введіть дані у правильному форматі.",
        "error",
      );
      return;
    }

    try {
      const formData = new URLSearchParams();
      formData.append("username", username);
      formData.append("password", password);

      const response = await fetch("http://46.101.245.42/login/", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: formData,
      });

      if (response.ok) {
        const data = await response.json();
        console.log("Авторизація успішна!", data);
        localStorage.setItem("token", data.access_token);

        if (data.message === "Успішний вхід ADMIN") {
          sessionStorage.setItem("admin_surname", data.admin_surname);
          sessionStorage.setItem("access_token", data.access_token);
          sessionStorage.setItem("Admin", "admin")
          window.location.href = "http://localhost:3000/list_kp";
        } else if(data.message === "Успішний вхід COMPANY") {
          sessionStorage.setItem("KP_surname", data.company_name);
          sessionStorage.setItem("access_token", data.access_token);
          sessionStorage.setItem("KP", "Кп");
          window.location.href = "http://localhost:3000/kp_working";
        }
      } else {
        const errorData = await response.json();
        console.error("Помилка входу:", errorData);
        alert(errorData.detail || "Невірна пошта або пароль");
      }
    } catch (error) {
      console.error("Помилка мережі:", error);
      alert("Помилка підключення до сервера");
    }
  };

  return (
    <div className="w-screen h-screen pt-30 bg-[#FBF9F4]">
      <Header />
      <motion.div
        className="w-full h-[80%] flex-col text-center place-items-center pt-[3%]"
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.8, ease: "easeOut" }}
      >
        <motion.h1
          className="text-[#000] text-5xl font-semibold"
          initial={{ opacity: 0, y: -10 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ delay: 0.3 }}
        >
          Увійти
        </motion.h1>

        <motion.p
          className="mt-[1%] mb-[1%] text-[#000] font-light text-xl"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.5 }}
        >
          Введіть своє ім'я користувача та пароль, щоб здійснити вхід в акаунт
        </motion.p>

        <motion.form
          onSubmit={logIn}
          className="w-[60%] h-[40%] flex flex-col justify-center items-center gap-4"
          initial={{ opacity: 0, scale: 0.95 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ delay: 0.6 }}
        >
          {/* Логін */}
          {!emailValid && (
            <p className="text-red-500 text-sm -mt-2">
              Введіть коректну електронну пошту, наприклад: example@gmail.com
            </p>
          )}
          <input
            type="text"
            placeholder="Login"
            className={`bg-white w-[40%] h-[27%] rounded-lg px-6 drop-shadow-md transition-all duration-300 ease-in-out 
             ${emailValid ? "focus:ring-2 focus:ring-blue-400" : "border-2 border-red-500"} focus:outline-none`}
            value={username}
            onChange={(e) => validateEmail(e.target.value)}
            required
          />

          {/* Пароль */}
          <input
            type="password"
            placeholder="*****"
            className={`bg-white w-[40%] h-[27%] rounded-lg px-6 drop-shadow-md transition-all duration-300 ease-in-out 
             ${passwordValid ? "focus:ring-2 focus:ring-blue-400" : "border-2 border-red-500"} focus:outline-none`}
            value={password}
            onChange={(e) => validatePassword(e.target.value)}
            required
          />

          {/* Кнопка */}
          <button
            className="bg-[rgba(255,190,125,0.43)] cursor-pointer w-90 h-12 rounded-lg text-[#FBF9F4] text-xl font-semibold uppercase transition duration-300 hover:bg-[rgba(255,190,125,0.7)] hover:text-white hover:shadow-lg"
            type="submit"
          >
            Увійти
          </button>
        </motion.form>
      </motion.div>
    </div>
  );
}
