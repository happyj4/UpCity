import { useState } from "react";

export function Inwork() {
  const appeals = [
    {
      number: "#1-2634",
      text: "Сміття",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Сміття",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Ями на дорозі",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Сміття",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#589D51]",
      text_color: "text-[#589D51]",
      button_color: "bg-[#EBFFEE]",
    },
    {
      number: "#1-2634",
      text: "Сміття",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
    {
      number: "#1-2634",
      text: "Відсутнє світло",
      status: "В роботі",
      date: "10.03.2025",
      title: "КП«Щасливе місто»",
      color: "bg-[#957A5E]",
      text_color: "text-[#957A5E]",
      button_color: "bg-[#FBF0E5]",
    },
  ];

  const [isVisible1, setIsVisible1] = useState(true);
  const [isVisible2, setIsVisible2] = useState(true);
  const [fullAppeals2, setFullAppeals2] = useState(appeals.slice(0, 12));
  const [fullAppeals1, setFullAppeals1] = useState(appeals.slice(0, 6));

  const change_status1 = () => {
    setIsVisible2(false);
    setTimeout(() => {
      setFullAppeals1(appeals.slice());
      setIsVisible1(true);
    }, 500);
  };

  const change_status2 = () => {
    setIsVisible1(false);
    setTimeout(() => {
      setFullAppeals2(appeals.slice());
      setIsVisible2(true);
    }, 500);
  };

  return (
    <div>
      <div
        className={`transition-all duration-700 ease-in-out overflow-hidden ${isVisible1 ? "opacity-100 max-h-[1000px]" : "opacity-0 max-h-0 pointer-events-none"}`}
      >
        <div className="w-full h-auto flex justify-between mt-10">
          <h1 className="text-[#3A3A3A] text-4xl font-semibold">В роботі</h1>
          <button
            className="text-[#BFBBB7] text-base font-medium uppercase cursor-pointer transition duration-300 ease-in-out hover:text-[#3A3A3A] hover:underline hover:scale-105"
            onClick={change_status1}
          >
            Переглянути всі
          </button>
        </div>
        <div className="w-full mt-8 flex gap-4 mb-10 flex-wrap">
          {fullAppeals1.map((item, index) => (
            <div
              key={index}
              className="w-56 h-35 bg-white rounded-lg flex-col py-2 px-2 drop-shadow-xl mb-4"
            >
              <p className="text-[#3A3A3A] text-xs font-light ">
                {item.number}
              </p>
              <h2 className="text-[#1E1E1E] text-2xl font-semibold mb-5">
                {item.text}
              </h2>
              <div className="flex w-full h-auto px-2 gap-2 mb-2">
                <div
                  className={`w-20 h-6 ${item.button_color} flex items-center px-1 gap-1 rounded-sm`}
                >
                  <div className={`w-2 h-2 rounded-4xl ${item.color}`}></div>
                  <span className={`${item.text_color} font-normal text-sm`}>
                    {item.status}
                  </span>
                </div>
                <div className="w-20 h-6 bg-[#EDEDED] flex items-center px-1 gap-1 rounded-sm">
                  <span className="text-[#848484] font-normal text-sm">
                    {item.date}
                  </span>
                </div>
              </div>
              <span className="text-[#848484] text-xs font-light">
                {item.title}
              </span>
            </div>
          ))}
        </div>
      </div>

      <div
        className={`transition-all duration-700 ease-in-out overflow-hidden ${isVisible2 ? "opacity-100 max-h-[1000px]" : "opacity-0 max-h-0 pointer-events-none"}`}
      >
        <div className="w-full h-auto flex justify-between">
          <h1 className="text-[#3A3A3A] text-4xl font-semibold mt-10">
            Архів Звернень
          </h1>
        </div>
        <div className="w-362 h-auto mt-8 flex flex-wrap gap-4">
          {fullAppeals2.map((item, index) => (
            <div
              key={index}
              className="w-56 h-35 bg-white rounded-lg flex-col py-2 px-2 drop-shadow-xl mb-4"
            >
              <p className="text-[#3A3A3A] text-xs font-light ">
                {item.number}
              </p>
              <h2 className="text-[#1E1E1E] text-2xl font-semibold mb-5">
                {item.text}
              </h2>
              <div className="flex w-full h-auto px-2 gap-2 mb-2">
                <div
                  className={`w-20 h-6 ${item.button_color} flex items-center px-1 gap-1 rounded-sm`}
                >
                  <div className={`w-2 h-2 rounded-4xl ${item.color}`}></div>
                  <span className={`${item.text_color} font-normal text-sm`}>
                    {item.status}
                  </span>
                </div>
                <div className="w-20 h-6 bg-[#EDEDED] flex items-center px-1 gap-1 rounded-sm">
                  <span className="text-[#848484] font-normal text-sm">
                    {item.date}
                  </span>
                </div>
              </div>
              <span className="text-[#848484] text-xs font-light">
                {item.title}
              </span>
            </div>
          ))}
        </div>
        <div className="w-full h-auto flex justify-center mt-5 mb-40">
          <button
            className="text-[#BFBBB7] text-base font-medium uppercase cursor-pointer transition duration-300 ease-in-out hover:text-[#3A3A3A] hover:underline hover:scale-105"
            onClick={change_status2}
          >
            Переглянути всі
          </button>
        </div>
      </div>
    </div>
  );
}
