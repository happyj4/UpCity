import React, { useEffect, useState } from "react";
import Image from "next/image";
import Link from "next/link";

export function Inwork() {
  const [appeal, setAppeals] = useState([]);
  const [appealEnded, setAppealEnded] = useState([]);
  const [appealsWork, setAppealsWork] = useState([]);
  const [isVisible1, setIsVisible1] = useState(true);
  const [isVisible2, setIsVisible2] = useState(true);
  const [try1, setTry1] = useState(true);
  const [try2, setTry2] = useState(true);

  useEffect(() => {
    const token = sessionStorage.getItem("access_token");

    fetch("http://46.101.245.42/application/all_by_user", {
      method: "GET",
      headers: {
        Accept: "application/json",
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        setAppeals(data);
      })
      .catch((error) => {
        console.error("❌ Помилка при завантаженні:", error);
      });
  }, []);

  useEffect(() => {
    const inWork = appeal.filter((item) => item.status === "В роботі");
    const notInWork = appeal.filter((item) => item.status !== "В роботі");

    setAppealsWork(inWork.slice(0, 6));
    setAppealEnded(notInWork.slice(0, 12));
  }, [appeal]);

  const change_status1 = () => {
    if (try1) {
      setIsVisible2(false);
      setTimeout(() => {
        const inWork = appeal.filter((item) => item.status === "В роботі");
        setAppealsWork(inWork);
        setIsVisible1(true);
        setTry1(false);
      }, 500);
    } else {
      setIsVisible2(true);
      setTimeout(() => {
        const inWork = appeal.filter((item) => item.status === "В роботі");
        setAppealsWork(inWork.slice(0, 6));
        setIsVisible1(true);
        setTry1(true);
      }, 500);
    }
  };

  const change_status2 = () => {
    if (try2) {
      setIsVisible1(false);
      setTimeout(() => {
        const notInWork = appeal.filter((item) => item.status !== "В роботі");
        setAppealEnded(notInWork);
        setIsVisible2(true);
        setTry2(false);
      }, 500);
    } else {
      setIsVisible1(true);
      setTimeout(() => {
        const notInWork = appeal.filter((item) => item.status !== "В роботі");
        setAppealEnded(notInWork.slice(0, 12));
        setIsVisible2(true);
        setTry2(true);
      }, 500);
    }
  };

  return (
    <div>
      <div className="w-[35%] flex gap-3 items-center pl-10">
        <div className="relative w-[90%]">
          <input
            type="text"
            className="w-full h-12 pl-12 pr-4 bg-white rounded-2xl border border-gray-200 shadow-sm focus:outline-none focus:ring-4 focus:ring-[#FFBE7D]/50 focus:border-[#FFBE7D] transition-all duration-300 hover:shadow-md text-sm text-gray-700 placeholder-gray-400"
            placeholder="Пошук..."
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
        <button className="w-10 h-10 bg-[#FFBE7D] hover:bg-[#f7ad5c] transition-all duration-300 rounded-md place-content-center flex cursor-pointer">
          <Image
            src="/images/Filter.svg"
            alt="filter"
            width={22}
            height={26}
            unoptimized
          />
        </button>
      </div>

      <div className="px-12">
        {/* В роботі */}
        <div
          className={`transition-all duration-700 ease-in-out overflow-hidden ${
            isVisible1
              ? "opacity-100 max-h-[1000px]"
              : "opacity-0 max-h-0 pointer-events-none"
          }`}
        >
          <div className="w-full h-auto flex justify-between mt-10">
            <h1 className="text-[#3A3A3A] text-4xl font-semibold">В роботі</h1>
            <button
              className="text-[#BFBBB7] mr-10 text-base font-medium uppercase cursor-pointer transition duration-300 ease-in-out hover:text-[#3A3A3A] hover:underline hover:scale-105"
              onClick={change_status1}
            >
              {try1 ? "Переглянути всі" : "Згорнути"}
            </button>
          </div>
          <div className="w-full mt-8 flex gap-4 mb-10 flex-wrap">
            {appealsWork.map((item) => (
              <Link
                href={`/kp#${item.application_id}`}
                key={item.application_id}
              >
                <div className="w-56 h-35 bg-white rounded-lg flex-col py-2 px-2 drop-shadow-xl mb-4 cursor-pointer transition-all duration-300 ease-in-out hover:scale-105 hover:shadow-2xl">
                  <p className="text-[#3A3A3A] text-xs font-light">
                    #{item.application_id}
                  </p>
                  <h2 className="text-[#1E1E1E] text-2xl font-semibold mb-5">
                    {item.name}
                  </h2>
                  <div className="flex w-full h-auto px-2 gap-2 mb-2">
                    <div className="w-20 h-6 bg-[#FBF0E5] flex items-center px-1 gap-1 rounded-sm">
                      <div className="w-2 h-2 rounded-full bg-[#957A5E]"></div>
                      <span className="text-[#957A5E] font-normal text-sm">
                        {item.status}
                      </span>
                    </div>
                    <div className="w-20 h-6 bg-[#EDEDED] flex items-center px-1 gap-1 rounded-sm">
                      <span className="text-[#848484] font-normal text-sm">
                        {item.application_date.slice(0, 10)}
                      </span>
                    </div>
                  </div>
                  <span className="text-[#848484] text-xs font-light">
                    КП «{item.utility_company.name}»
                  </span>
                </div>
              </Link>
            ))}
          </div>
        </div>

        {/* Архів */}
        <div
          className={`transition-all duration-700 ease-in-out overflow-hidden ${
            isVisible2
              ? "opacity-100 max-h-[1000px]"
              : "opacity-0 max-h-0 pointer-events-none"
          }`}
        >
          <div className="w-full h-auto flex justify-between">
            <h1 className="text-[#3A3A3A] text-4xl font-semibold mt-10">
              Архів Звернень
            </h1>
          </div>
          <div className="w-362 h-auto mt-8 flex flex-wrap gap-4">
            {appealEnded.map((item) => (
              <Link
                href={`/kpEnded#${item.application_id}`}
                key={item.application_id}
              >
                <div className="w-56 h-35 bg-white rounded-lg flex-col py-2 px-2 drop-shadow-xl mb-4 cursor-pointer transition-all duration-300 ease-in-out hover:scale-105 hover:shadow-2xl">
                  <p className="text-[#3A3A3A] text-xs font-light">
                    #{item.application_id}
                  </p>
                  <h2 className="text-[#1E1E1E] text-2xl font-semibold mb-5">
                    {item.name}
                  </h2>
                  <div className="flex w-full h-auto px-2 gap-2 mb-2">
                    <div
                      className={`w-20 h-6 ${
                        item.status === "Виконано"
                          ? "bg-[#EBFFEE]"
                          : "bg-[#EDEDED]"
                      } flex items-center px-1 gap-1 rounded-sm`}
                    >
                      <div className="w-2 h-2 rounded-full bg-[#589D51]"></div>
                      <span
                        className={`${
                          item.status === "Виконано"
                            ? "text-[#589D51]"
                            : "text-[#848484]"
                        } font-normal text-sm`}
                      >
                        {item.status}
                      </span>
                    </div>
                    <div className="w-20 h-6 bg-[#EDEDED] flex items-center px-1 gap-1 rounded-sm">
                      <span className="text-[#848484] font-normal text-sm">
                        {item.application_date.slice(0, 10)}
                      </span>
                    </div>
                  </div>
                  <span className="text-[#848484] text-xs font-light">
                    КП «{item.utility_company.name}»
                  </span>
                </div>
              </Link>
            ))}
          </div>
          <div className="w-full h-auto flex justify-center mt-5 mb-40">
            <button
              className="text-[#BFBBB7] text-base font-medium uppercase cursor-pointer transition duration-300 ease-in-out hover:text-[#3A3A3A] hover:underline hover:scale-105"
              onClick={change_status2}
            >
              {try2 ? "Переглянути всі" : "Згорнути"}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
