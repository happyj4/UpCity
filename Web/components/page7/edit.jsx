import { useEffect, useState } from "react";
import Swal from "sweetalert2";
import { motion } from "framer-motion";

export function Edit() {
  const [data, setData] = useState({
    name: "",
    address: "",
    phone: "",
    email: "",
  });
  const [errors, setErrors] = useState({
    name: "",
    address: "",
    phone: "",
    email: "",
  });
  const hashId = window.location.hash.slice(1);

  useEffect(() => {
    const token = sessionStorage.getItem("access_token");

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

  const validate = (field, value) => {
    switch (field) {
      case "name":
        if (!value || value.length < 8) {
          return "Назва має містити мінімум 8 символів";
        }
        return "";
      case "address":
        if (!value || value.length < 8) {
          return "Адреса має містити мінімум 8 символів";
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

    setData((prev) => ({
      ...prev,
      [name]: value,
    }));

    const errorMsg = validate(name, value);
    setErrors((prev) => ({
      ...prev,
      [name]: errorMsg,
    }));
  };

  const isValid = () => {
    const newErrors = {};
    Object.entries(data).forEach(([key, val]) => {
      newErrors[key] = validate(key, val);
    });
    setErrors(newErrors);
    return Object.values(newErrors).every((e) => e === "");
  };

  const handleSubmit = async () => {
    if (!isValid()) {
      Swal.fire(
        "Неправильний формат",
        "Будь ласка введіть дані у правильному форматі.",
        "error"
      );
      return;
    }

    const token = sessionStorage.getItem("access_token");

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
        }
      );

      if (!response.ok) {
        throw new Error("Помилка при збереженні даних");
      }

      const result = await response.json();
      console.log("Успішно оновлено:", result);
      Swal.fire({
        icon: "success",
        title: "Відредаговано",
        text: "Дані успішно оновлені",
        confirmButtonText: "OK",
      }).then((result) => {
        if (result.isConfirmed) {
          window.location.href = "http://localhost:3000/list_kp";
        }
      });
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
      <motion.div
        initial={{ opacity: 0, y: 40 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6, ease: "easeOut" }}
        className="flex justify-center w-full h-auto"
      >
        <motion.div
          className="flex flex-col h-auto w-[25%]"
          initial="hidden"
          animate="visible"
          variants={{
            visible: {
              transition: {
                staggerChildren: 0.1,
              },
            },
          }}
        >
          {["name", "address", "phone", "email"].map((field, index) => (
            <motion.div
              key={field}
              variants={{
                hidden: { opacity: 0, y: 20 },
                visible: { opacity: 1, y: 0 },
              }}
            >
              <label className="text-[#3A3A3A] text-base font-light">
                {{
                  name: "Назва комунального підприємства",
                  address: "Адреса комунального підприємства",
                  phone: "Номер телефону комунального підприємства",
                  email: "Електронна пошта комунального підприємства",
                }[field]}
              </label>
              <input
                type="text"
                name={field}
                value={data[field]}
                onChange={handleChange}
                placeholder={{
                  name: "Введіть назву",
                  address: "Введіть адресу",
                  phone: "+380123456789",
                  email: "email@example.com",
                }[field]}
                className={`w-full h-16 rounded-lg bg-[#FFFEFC] focus:bg-white focus:border-orange-400 focus:ring-2 focus:ring-orange-200 outline-none drop-shadow-md mt-2 mb-1 pl-6 transition-all duration-300 ${
                  errors[field] ? "border border-red-600" : ""
                }`}
              />
              {errors[field] && (
                <div className="text-red-600 text-sm mb-3">
                  {errors[field]}
                </div>
              )}
            </motion.div>
          ))}

          <motion.button
            onClick={handleSubmit}
            className="w-full mt-10 mb-30 cursor-pointer h-14 bg-[rgba(255,190,125,0.8)] hover:bg-orange-400 transition-all duration-300 transform hover:scale-105 uppercase rounded-lg text-white text-xl font-semibold shadow-md hover:shadow-2xl"
            whileHover={{ scale: 1.05 }}
            whileTap={{ scale: 0.95 }}
            variants={{
              hidden: { opacity: 0, y: 20 },
              visible: { opacity: 1, y: 0 },
            }}
          >
            зберегти
          </motion.button>
        </motion.div>
      </motion.div>
    </div>
  );
}
