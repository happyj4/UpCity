"use client";
import Image from "next/image";
import { useEffect, useState } from "react";
import { motion } from "framer-motion";

export function Items() {
  const [items, setItems] = useState([]);

  useEffect(() => {
    fetch("https://718b-46-150-17-217.ngrok-free.app/products/", {
      method: "GET",
      headers: {
        Accept: "application/json",
        "ngrok-skip-browser-warning": "true",
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error("HTTP error " + res.status);
        return res.json();
      })
      .then((data) => {
        setItems(data);
      })
      .catch((err) => {
        console.error("Ошибка при получении данных:", err);
      });
  }, []);

  const add = (productId) => {
    fetch("https://718b-46-150-17-217.ngrok-free.app/cart/", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify({
        product_id: productId,
        quantity: 1,
      }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Не вдалося додати товар у кошик");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Успішно додано:", data);
      })
      .catch((error) => {
        console.error(error.message);
      });
  };

  return (
    <div className="w-full h-auto flex flex-wrap mt-10 px-20 gap-24 mb-50">
      {items.map((item, index) => {
        return (
          <div className="w-68 h-110 flex flex-col " key={item.id}>
            <div className="w-full h-[70%]">
              <Image
                src="/images/czegla.jpg"
                alt="loop"
                width={273}
                height={500}
                unoptimized
              />
            </div>
            <h2 className="text-[#3A3A3A] text-xl font-light uppercase">
              {item.name}
            </h2>
            <span className="text-[#3A3A3A] text-base font-light">
              {item.description}
            </span>
            <div className="w-full h-auto flex justify-between mt-6 items-center">
              <span className="#3A3A3A font-xl font-bold">
                {item.price} грн
              </span>
              <button
                className="px-6 py-3 rounded-2xl cursor-pointer bg-[#E2B990] text-white text-lg font-semibold uppercase shadow-md hover:bg-[#d8a86c] transition-colors duration-300"
                onClick={() => add(item.id)}
              >
                в кошик
              </button>
            </div>
          </div>
        );
      })}
    </div>
  );
}
