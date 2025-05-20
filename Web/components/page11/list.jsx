import Image from "next/image";
import Link from "next/link";
import { useEffect, useState } from "react";

export function List() {
  const [items, setItems] = useState([]);
  const [error, setError] = useState(null);
  const isEmpty = !items || items.length === 0;

  useEffect(() => {
    fetch("https://718b-46-150-17-217.ngrok-free.app/cart/", {
      method: "GET",
      headers: {
        Accept: "application/json",
        "ngrok-skip-browser-warning": "true",
      },
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error("Не вдалося завантажити кошик");
        }
        return res.json();
      })
      .then((data) => setItems(data))
      .catch((err) => setError(err.message));
  }, []);

  function deleteAll() {
    fetch("https://718b-46-150-17-217.ngrok-free.app/cart/", {
      method: "DELETE",
      headers: { Accept: "*/*" },
    })
      .then((response) => {
        if (!response.ok) {
          return response.json().then((data) => {
            throw new Error(data.detail || "Помилка при очищенні кошика");
          });
        }
        setItems([]);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  function minus(index) {
    const newItems = [...items];
    if (newItems[index].quantity > 0) {
      newItems[index].quantity -= 1;
      setItems(newItems);
    }
  }

  function plus(index) {
    const newItems = [...items];
    newItems[index].quantity += 1;
    setItems(newItems);
  }

  const total = items
    .reduce((sum, item) => {
      return sum + Number(item.price) * Number(item.quantity);
    }, 0)
    .toFixed(2);

  function deletion(index, id) {
    fetch(`https://718b-46-150-17-217.ngrok-free.app/cart/${id}/`, {
      method: "DELETE",
      headers: {
        Accept: "*/*",
      },
    })
      .then((response) => {
        if (!response.ok) {
          return response.json().then((data) => {
            throw new Error(data.detail || "Помилка при видаленні");
          });
        }
        setItems((prevItems) => prevItems.filter((_, i) => i !== index));
        console.log(`Товар з id=${id} успішно видалено`);
      })
      .catch((error) => {
        console.error("Помилка:", error.message);
      });
  }

  return (
    <div>
      <div>
        <div className="w-full h-auto flex justify-between pt-40 px-20">
          <h1 className="text-[#000] text-4xl font-light uppercase">кошик</h1>
          <button
            className="w-46 h-10 border cursor-pointer border-[#848484] p-4 rounded-3xl flex items-center justify-center text-[#848484] uppercase text-base font-light transition-all duration-300 hover:bg-[#848484] hover:text-white hover:shadow-md active:scale-95"
            onClick={() => deleteAll()}
          >
            очистити кошик
          </button>
        </div>
        <div
          className={`${isEmpty ? "hidden" : "block"} w-full h-auto flex text-[#000] text-base font-light uppercase gap-10 mt-12 pl-100`}
        >
          <span>товар</span>
          <span className="pl-82">ціна</span>
          <span className="pl-38 pr-45">кількість</span>
          <span>вартість</span>
        </div>
      </div>
      <h1
        className={` ${isEmpty ? "block" : "hidden"} uppercase text-[#848484] text-4xl font-bold flex justify-center mt-6 mb-60`}
      >
        покищо тут пусто
      </h1>
      <div className="w-full h-auto mt-5 px-20">
        <ul className={`${isEmpty ? "hidden" : "block"}`}>
          {items.map((item, index) => {
            return (
              <li key={index}>
                <div className="w-full h-32 flex border-t border-b border-[#EDEDED] items-center hover:bg-[#F9F9F9] hover:shadow-md transition-all duration-300 cursor-pointer rounded-xl px-4">
                  <div className="w-29 h-auto">
                    <Image
                      src="/images/czegla.jpg"
                      alt="loop"
                      width={116}
                      height={66}
                      unoptimized
                    />
                  </div>
                  <div className="w-auto h-full place-content-center flex flex-col ml-8">
                    <h3 className="text-black text-xl font-medium mb-8">
                      {item.name}
                    </h3>
                    <span
                      className="text-[#848484] text-base font-light underline hover:text-black active:scale-95 transition-all duration-200"
                      onClick={() => deletion(index, item.id)}
                    >
                      Видалити
                    </span>
                  </div>
                  <p className="text-black text-xl font-medium ml-55">
                    {item.price} грн/шт
                  </p>
                  <div className="flex h-full place-content-center items-center gap-2 ml-35">
                    <div
                      className="w-4 h-[10px] place-content-center cursor-pointer hover:opacity-80 transition duration-200"
                      onClick={() => minus(index)}
                    >
                      <Image
                        src="/images/minus_shop.png"
                        alt="loop"
                        width={16}
                        height={1}
                        unoptimized
                      />
                    </div>
                    <button className="w-11 h-7 rounded-3xl flex items-center place-content-center bg-[#EAEAEA] text-[#3A3A3A] text-base font-light">
                      {item.quantity}
                    </button>
                    <div
                      className="w-4 h-[14px] cursor-pointer hover:opacity-80 transition duration-200"
                      onClick={() => plus(index)}
                    >
                      <Image
                        src="/images/plus_shop.png"
                        alt="loop"
                        width={16}
                        height={1}
                        unoptimized
                      />
                    </div>
                    <span className="text-black text-xl font-medium ml-40">
                      {(items[index].price * items[index].quantity).toFixed(2)}{" "}
                      грн
                    </span>
                  </div>
                </div>
              </li>
            );
          })}
        </ul>
        <div className="w-full h-auto flex mt-10 gap-10 items-center mb-40">
          <button className="w-78 h-13 cursor-pointer bg-[#E2B98F] text-white uppercase font-semibold text-xl flex items-center justify-center rounded-3xl transition-all duration-300 hover:bg-[#d1a974] hover:shadow-lg active:scale-95">
            оформити замовлення
          </button>
          <Link href="http://localhost:3000/shop">
          <button className="w-78 h-13 cursor-pointer border border-[#848484] text-[#B7B7B7] uppercase font-semibold text-xl flex items-center justify-center rounded-3xl transition-all duration-300 hover:border-[#6e6e6e] hover:text-[#6e6e6e] hover:shadow-md active:scale-95">
            продовжити покупки
          </button>
          </Link>

          <span className="text-[#000] text-2xl font-semibold uppercase pl-50">
            Вартість замовлення: {total}грн
          </span>
        </div>
      </div>
    </div>
  );
}
