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
    image: "/images/Anna.jpg",
    description:
      "Розробниця бази даних, займається структурою, оптимізацією та безпекою зберігання даних для стабільної роботи платформи.",
  },
  {
    name: "кукуленко денис",
    image: "/images/Denys.png",
    description:
      "Фронтенд розробник сайту, відповідає за цілісну технічну реалізацію проєкту, інтеграцію з backend та підтримку сайту.",
  },
  {
    name: "кирриченко кирил",
    image: "/images/Kyrylo.jpg",
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
    image: "/images/Serj.png",
    description:
      "Бекенд розробник, створює серверну логіку, API та забезпечує обробку запитів і взаємодію з базами даних.",
  },
];

const containerVariant = {
  hidden: { opacity: 0, y: 40 },
  visible: {
    opacity: 1,
    y: 0,
    transition: { duration: 0.8, ease: "easeOut" },
  },
};

const cardVariants = {
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

export function MiddlePage() {
  const [index, setIndex] = useState(0);
  const [direction, setDirection] = useState(1);

  const paginate = (newDirection) => {
    setDirection(newDirection);
    setIndex(
      (prev) => (prev + newDirection * 2 + developers.length) % developers.length
    );
  };

  const visibleDevelopers = [
    developers[index % developers.length],
    developers[(index + 1) % developers.length],
  ];

  return (
    <motion.div
      className="w-full h-auto bg-white mt-50 flex-col text-center py-12 overflow-hidden"
      variants={containerVariant}
      initial="hidden"
      whileInView="visible"
      viewport={{ once: true, amount: 0.3 }}
    >
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
        <motion.button
          onClick={() => paginate(-1)}
          className="z-10 w-13 h-13 bg-white rounded-4xl drop-shadow-2xl flex place-content-center items-center cursor-pointer"
          whileHover={{ scale: 1.2, backgroundColor: "#333" }}
          transition={{ duration: 0.3 }}
        >
          <Image
            className="rotate-180"
            src="/images/Arrow-right.svg"
            alt="arrow_left"
            width={18}
            height={21}
            unoptimized
          />
        </motion.button>

        <div className="flex gap-10 w-[800px] h-[300px] justify-center items-center relative overflow-hidden">
          <AnimatePresence custom={direction}>
            {visibleDevelopers.map((dev, i) => (
              <motion.div
                key={dev.name + index + "-" + i}
                className="bg-white rounded-2xl w-110 h-70 drop-shadow-xl flex flex-col items-center justify-start p-8 cursor-pointer"
                custom={direction}
                variants={cardVariants}
                initial="enter"
                animate="center"
                exit="exit"
                transition={{ duration: 0.5, ease: "easeInOut" }}
                whileHover={{ scale: 1.05, boxShadow: "0 15px 25px rgba(0,0,0,0.2)" }}
              >
                <motion.div whileHover={{ scale: 1.1 }} transition={{ duration: 0.3 }}>
                  <Image src={dev.image} alt={dev.name} width={80} height={80} unoptimized />
                </motion.div>
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

        <motion.button
          onClick={() => paginate(1)}
          className="z-10 w-13 h-13 bg-white rounded-4xl drop-shadow-2xl flex place-content-center items-center cursor-pointer"
          whileHover={{ scale: 1.2, backgroundColor: "#333" }}
          transition={{ duration: 0.3 }}
        >
          <Image
            src="/images/Arrow-right.svg"
            alt="arrow_right"
            width={18}
            height={21}
            unoptimized
          />
        </motion.button>
      </div>
    </motion.div>
  );
}
