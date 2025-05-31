import { useState } from "react";
import Swal from "sweetalert2";
import { motion } from "framer-motion";

export function Add() {
  const [formData, setFormData] = useState({
    name: "",
    address: "",
    phone: "",
    email: "",
    password: "",
  });

  const [errors, setErrors] = useState({
    name: "",
    address: "",
    phone: "",
    email: "",
    password: "",
  });

  const validate = (field, value) => {
    switch (field) {
      case "name":
      case "address":
        if (!value || value.length < 10) {
          return "Поле повинно містити щонайменше 10  символів";
        }
      case "password":
        if (!value || value.length < 8) {
          return "Поле повинно містити щонайменше 8 символів";
        }
        return "";
      case "phone":
        if (!/^(\+?380)?\d{9}$/.test(value.replace(/\D/g, ""))) {
          return "Введіть коректний номер телефону (наприклад, +380123456789)";
        }
        return "";
      case "email":
        if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value)) {
          return "Введіть коректну електронну пошту";
        }
        return "";
      default:
        return "";
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
    setErrors({
      ...errors,
      [name]: validate(name, value),
    });
  };

  const isValid = () => {
    const newErrors = {};
    Object.entries(formData).forEach(([key, value]) => {
      newErrors[key] = validate(key, value);
    });
    setErrors(newErrors);
    return Object.values(newErrors).every((err) => err === "");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!isValid()) {
      Swal.fire(
        "Неправильний формат",
        "Будь ласка введіть дані у правильному форматі.",
        "error",
      );
      return;
    }

    const token = sessionStorage.getItem("access_token");

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
        Swal.fire({
          icon: "success",
          title: "Підприємство додано",
          text: "Дані збережені",
          confirmButtonText: "OK",
        }).then((result) => {
          if (result.isConfirmed) {
            window.location.href = "http://localhost:3000/list_kp";
          }
        });
      } else {
        const errorData = await response.json();
        alert("Помилка: " + JSON.stringify(errorData));
      }
    } catch (error) {
      alert("Мережева помилка: " + error.message);
    }
  };

  return (
    <motion.div
      className="w-full h-auto pt-20"
      initial={{ opacity: 0, y: 50 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.6, ease: "easeOut" }}
    >
      <h1 className="text-[#3A3A3A] text-4xl font-semibold mt-15 mb-15 pl-25">
        Додавання комунального підприємства
      </h1>
      <div className="flex justify-center w-full h-auto">
        <motion.form
          onSubmit={handleSubmit}
          className="flex flex-col h-auto w-[25%]"
          initial={{ opacity: 0, scale: 0.95 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ delay: 0.2, duration: 0.5 }}
        >
          {[
            {
              label: "Назва",
              name: "name",
              type: "text",
              placeholder: "Введіть назву",
            },
            {
              label: "Адреса",
              name: "address",
              type: "text",
              placeholder: "Введіть адресу",
            },
            {
              label: "Телефон",
              name: "phone",
              type: "text",
              placeholder: "+380...",
            },
            {
              label: "Пошта",
              name: "email",
              type: "text",
              placeholder: "email@example.com",
            },
            {
              label: "Пароль",
              name: "password",
              type: "password",
              placeholder: "********",
            },
          ].map(({ label, name, type, placeholder }) => (
            <div key={name} className="mb-6">
              <span className="text-[#3A3A3A] text-base font-light">
                {label} комунального підприємства
              </span>
              <input
                type={type}
                name={name}
                value={formData[name]}
                onChange={handleChange}
                placeholder={placeholder}
                className={`w-full h-16 rounded-lg bg-[#FFFEFC] focus:bg-white focus:border-orange-400 focus:ring-2 focus:ring-orange-200 outline-none drop-shadow-md mt-2 pl-6 transition-all duration-300 ${
                  errors[name] ? "border border-red-600" : ""
                }`}
              />
              {errors[name] && (
                <div className="text-red-600 text-sm mt-1">{errors[name]}</div>
              )}
            </div>
          ))}

          <button
            type="submit"
            className="w-full mb-30 cursor-pointer h-14 bg-[rgba(255,190,125,0.8)] hover:bg-orange-400 transition-all duration-300 transform hover:scale-105 uppercase rounded-lg text-white text-xl font-semibold shadow-md hover:shadow-2xl"
          >
            зберегти
          </button>
        </motion.form>
      </div>
    </motion.div>
  );
}
