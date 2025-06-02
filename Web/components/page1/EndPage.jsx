import Image from "next/image";
import { motion } from "framer-motion";

const fadeInUpVariant = {
  hidden: { opacity: 0, y: 40, scale: 0.95 },
  visible: {
    opacity: 1,
    y: 0,
    scale: 1,
    transition: {
      duration: 0.7,
      ease: "easeOut",
    },
  },
};

export function EndPage() {
  return (
    <div className="w-screen h-auto flex  px-20 gap-10 justify-center pl-[10%] mb-10">
      <motion.div
        className="w-[50%] h-[100%] pt-30"
        variants={fadeInUpVariant}
        initial="hidden"
        whileInView="visible"
        viewport={{ once: true, amount: 0.4 }}
      >
        <div className="break-words w-[100%]">
          <p className="text-[#1E1E1E] text-5xl font-bold uppercase tracking-widest">
            Завантажте
          </p>
          <span className="font-normal text-[#1E1E1E] text-4xl uppercase">
            наш додаток
          </span>
        </div>
        <div className="w-[90%] h-auto break-words mt-8 mb-20">
          <p className="text-[#3A3A3A] text-base font-light">
            UpCity — це зручний мобільний інструмент для оперативного звернення до 
            міських служб. Повідомляйте про проблеми, відстежуйте статус звернень та отримуйте 
            сповіщення про їх вирішення — все в одному місці. Разом ми зробимо наше місто комфортнішим!
          </p>
        </div>
        <motion.div
          whileHover={{ scale: 1.15 }}
          transition={{ type: "spring", stiffness: 300 }}
          className="inline-block cursor-pointer drop-shadow-2xl"
        >
          <Image
            src="/images/button-download.png"
            alt="download"
            width={186}
            height={61}
            unoptimized
            quality={100}
          />
        </motion.div>
      </motion.div>

      <motion.div
        className="w-[50%] h-140 drop-shadow-2xl"
        variants={fadeInUpVariant}
        initial="hidden"
        whileInView="visible"
        viewport={{ once: true, amount: 0.4 }}
        transition={{ delay: 0.2 }}
      >
        <Image
          src="/images/Redmi_Note_11.png"
          alt="redminote"
          width={545}
          height={559}
          unoptimized
          quality={100}
        />
      </motion.div>
    </div>
  );
}
