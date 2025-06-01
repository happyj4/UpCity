import Image from "next/image";
import { motion } from "framer-motion";

const containerVariants = {
  hidden: { opacity: 0, y: 40 },
  visible: {
    opacity: 1,
    y: 0,
    transition: { duration: 0.8, ease: "easeOut" },
  },
};

const imageHover = {
  hover: {
    scale: 1.05,
    rotate: 2,
    transition: { duration: 0.6, ease: "easeOut" },
  },
};

export function EndPage() {
  return (
    <motion.div
      className="w-screen h-240 flex place-items-center justify-center gap-10 px-10"
      variants={containerVariants}
      initial="hidden"
      whileInView="visible"
      viewport={{ once: true, amount: 0.3 }}
    >
      <motion.div className="w-[40%] h-141" whileHover="hover" variants={imageHover}>
        <Image
          className="mt-12 mb-4 rounded-lg shadow-lg"
          src="/images/city.png"
          alt="city"
          width={579}
          height={567}
          unoptimized
        />
      </motion.div>
      <div className="w-[35%] h-141 flex flex-col py-12">
        <h3 className="uppercase text-[#F58E27] drop-shadow-xl text-sm font-medium mb-3">
          Наша місія та бачення
        </h3>
        <h1 className="uppercase text-[#1E1E1E] font-semibold text-4xl">
          Наша місія та бачення
        </h1>
        <h3 className="text-[#1E1E1E] drop-shadow-md text-2xl font-semibold mt-10 mb-2">
          <span className="text-[#F58E27]">-</span> Місія
        </h3>
        <span className="text-2xl text-[#1E1E1E] font-light mb-10">
          Наша місія — покращити міське середовище завдяки інноваційним
          технологіям та активній участі мешканців. Ми прагнемо створити
          простір, де кожен зможе відчути себе залученим до розвитку свого
          міста.
        </span>
        <h3 className="text-[#1E1E1E] drop-shadow-md text-2xl font-semibold mt-10 mb-2">
          <span className="text-[#F58E27]">-</span> Бачення
        </h3>
        <span className="text-2xl text-[#1E1E1E] font-light">
          Ми бачимо майбутнє, де громадяни — активні учасники змін, а рішення
          приймаються на основі прозорої аналітики та відкритої взаємодії.
        </span>
      </div>
    </motion.div>
  );
}
