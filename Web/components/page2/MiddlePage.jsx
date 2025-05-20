import Image from "next/image";
import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";

const developers = [
  {
    name: "денисенко єлизавета",
    image: "/images/Liza.png",
    description:
      "UI/UX дизайнер, відповідає за зручність і естетику інтерфейсу, створює макети та взаємодіє з розробниками для їх реалізації.",
  },
  {
    name: "бут анна",
    image: "/images/Anna.png",
    description:
      "Розробниця бази даних, займається структурою, оптимізацією та безпекою зберігання даних для стабільної роботи платформи.",
  },
  {
    name: "кукуленко денис",
    image: "/images/Denis.png",
    description:
      "Фронтенд розробник сайту, відповідає за цілісну технічну реалізацію проєкту, інтеграцію з backend та підтримку сайту.",
  },
  {
    name: "кирриченко кирил",
    image: "/images/Kiril.png",
    description:
      "Реалізує інтерактивну частину сайту, забезпечує адаптивність і швидкодію інтерфейсу.",
  },
  {
    name: "корякін захар",
    image: "/images/Zahar.png",
    description:
      "Розробник мобільного застосунку, адаптує функціонал платформи під Android, забезпечує стабільність і зручність користування.",
  },
  {
    name: "козлов сергій",
    image: "/images/Sergey.png",
    description:
      "Бекенд розробник, створює серверну логіку, API та забезпечує обробку запитів і взаємодію з базами даних.",
  },
];

export function MiddlePage() {
  const [index, setIndex] = useState(0);
  const [direction, setDirection] = useState(1);

  const paginate = (newDirection) => {
    setDirection(newDirection);
    setIndex(
      (prev) =>
        (prev + newDirection * 2 + developers.length) % developers.length,
    );
  };

  const variants = {
    enter: (dir) => ({
      x: dir > 0 ? 400 : -400,
      opacity: 0,
      position: "absolute",
    }),
    center: {
      x: 0,
      opacity: 1,
      position: "relative",
    },
    exit: (dir) => ({
      x: dir < 0 ? 400 : -400,
      opacity: 0,
      position: "absolute",
    }),
  };

  const visibleDevelopers = [
    developers[index % developers.length],
    developers[(index + 1) % developers.length],
  ];

  return (
    <div className="w-full h-auto bg-white mt-50 flex-col text-center py-12 overflow-hidden">
      <span className="uppercase text-[#FFBE7D] text-sm font-medium drop-shadow-lg">
        Розробники
      </span>
      <h2 className="uppercase text-[#1E1E1E] text-4xl font-semibold mt-3 mb-3">
        все для кращого життя
      </h2>
      <span className="uppercase text-[#1E1E1E] font-light text-sm">
        Ми наймаємо лише найкращих експертів, щоб забезпечити зручність та
        ефективність нашого продукту
      </span>

      <div className="flex place-content-center w-full gap-16 mt-10 items-center relative">
        <button
          onClick={() => paginate(-1)}
          className="z-10 w-13 h-13 bg-white rounded-4xl drop-shadow-2xl flex place-content-center items-center transition-all duration-300 hover:bg-gray-800 hover:shadow-xl cursor-pointer"
        >
          <Image
            className="rotate-180"
            src="/images/Arrow-right.svg"
            alt="arrow_left"
            width={18}
            height={21}
            unoptimized
          />
        </button>

        <div className="flex gap-10 w-[800px] h-[300px] justify-center items-center relative overflow-hidden">
          <AnimatePresence custom={direction}>
            {visibleDevelopers.map((dev, i) => (
              <motion.div
                key={dev.name + index + "-" + i}
                className="bg-white rounded-2xl w-110 h-70 drop-shadow-xl flex flex-col items-center justify-start p-8"
                custom={direction}
                variants={variants}
                initial="enter"
                animate="center"
                exit="exit"
                transition={{ duration: 0.5, ease: "linear" }}
              >
                <Image
                  src={dev.image}
                  alt={dev.name}
                  width={80}
                  height={80}
                  unoptimized
                />
                <h2 className="uppercase text-[#FFBE7D] text-2xl font-semibold mt-4 mb-4">
                  {dev.name}
                </h2>
                <span className="text-[#1E1E1E] text-sm font-light">
                  {dev.description}
                </span>
              </motion.div>
            ))}
          </AnimatePresence>
        </div>

        <button
          onClick={() => paginate(1)}
          className="z-10 w-13 h-13 bg-white rounded-4xl drop-shadow-2xl flex place-content-center items-center transition-all duration-300 hover:bg-gray-800 hover:shadow-xl cursor-pointer"
        >
          <Image
            src="/images/Arrow-right.svg"
            alt="arrow_right"
            width={18}
            height={21}
            unoptimized
          />
        </button>
      </div>
    </div>
  );
}
