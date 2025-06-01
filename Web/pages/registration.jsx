import { Header } from "../components/WebComponents/header.jsx";
import React, { useState } from "react";

export default function RegistrationPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const logIn = async (e) => {
    e.preventDefault();

    try {
      const formData = new URLSearchParams();
      formData.append("username", username);
      formData.append("password", password);

      const response = await fetch("http://46.101.245.42/login/", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: formData,
      });

      if (response.ok) {
        const data = await response.json();
        console.log("Авторизація успішна!", data);
        localStorage.setItem("token", data.access_token);

        if (data.message == "Успішний вхід ADMIN") {
          sessionStorage.setItem("admin_surname", data.admin_surname);
          localStorage.setItem("access_token", data.access_token);
          window.location.href = "http://localhost:3000/list_kp";
        } else {
          window.location.href = "http://localhost:3000/kp_working";
        }
        // Можна перенаправити на іншу сторінку:
        // window.location.href = "/dashboard";
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
      <div className="w-full h-[80%] flex-col text-center place-items-center pt-[3%]">
        <h1 className="text-[#000] text-5xl font-semibold">Увійти</h1>
        <p className="mt-[1%] mb-[1%] text-[#000] font-light text-xl">
          Введіть своє ім'я користувача та пароль, щоб здійснити вхід в акаунт
        </p>
        <form
          onSubmit={logIn}
          className="w-[60%] h-[40%] flex flex-col justify-center items-center gap-4"
        >
          <input
            type="text"
            placeholder="username"
            className="bg-white w-[40%] h-[27%] rounded-lg px-6 drop-shadow-md"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <input
            type="password"
            placeholder="*****"
            className="bg-white w-[40%] h-[27%] rounded-lg px-6 drop-shadow-md"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button
            className="bg-[rgba(255,190,125,0.43)] cursor-pointer w-90 h-12 rounded-lg text-[#FBF9F4] text-xl font-semibold uppercase transition duration-300 hover:bg-[rgba(255,190,125,0.7)] hover:text-white hover:shadow-lg"
            type="submit"
          >
            Увійти
          </button>
        </form>
      </div>
    </div>
  );
}
