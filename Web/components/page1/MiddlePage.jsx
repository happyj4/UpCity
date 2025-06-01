import Image from "next/image";
import { motion } from "framer-motion";
import { Map } from "./Map";

const fadeUpScaleVariant = {
  hidden: { opacity: 0, y: 20, scale: 0.95 },
  visible: {
    opacity: 1,
    y: 0,
    scale: 1,
    transition: { duration: 0.6, ease: "easeOut" },
  },
};

export function MiddlePage() {
  return (
    <div className="w-screen h-140 flex px-20 gap-10 pl-25 ">
      <div className="w-[16%] h-auto items-center flex-col">
        <motion.div
          className="w-[100%] h-24 bg-white rounded-lg drop-shadow-lg mb-8 py-4 px-6 place-items-start flex-col text-[#1E1E1E] transition-transform duration-300 hover:scale-105 hover:shadow-2xl"
          variants={fadeUpScaleVariant}
          initial="hidden"
          whileInView="visible"
          viewport={{ once: true, amount: 0.3 }}
        >
          <p className="text-lg font-normal">Ями на дорозі</p>
          <p className="font-normal text-2xl">
            564 / <span className="font-light text-lg">6.09%</span>
          </p>
        </motion.div>

        <motion.div
          className="w-[100%] h-51 bg-white rounded-lg drop-shadow-lg mb-8 py-4 px-6 place-items-start flex-col text-[#1E1E1E] transition-transform duration-300 hover:scale-105 hover:shadow-2xl"
          variants={fadeUpScaleVariant}
          initial="hidden"
          whileInView="visible"
          viewport={{ once: true, amount: 0.3 }}
        >
          <p className="font-normal text-base mb-2">
            Всього виконаних звернень
          </p>
          <p className="font-normal text-2xl">
            2047/ <span className="font-light text-lg">2691</span>
          </p>
          <p className="text-[#339C27] text-base font-light mb-4">-5.93%</p>
          <p className="text-base font-semibold">Порівняно з минулим місяцем</p>
        </motion.div>

        <motion.div
          className="w-[100%] h-24 bg-white rounded-lg drop-shadow-lg mb-8 py-4 px-6 place-items-start flex-col text-[#1E1E1E] transition-transform duration-300 hover:scale-105 hover:shadow-2xl"
          variants={fadeUpScaleVariant}
          initial="hidden"
          whileInView="visible"
          viewport={{ once: true, amount: 0.3 }}
        >
          <p className="font-normal text-base ">Відсутнє світло</p>
          <p className="font-normal text-2xl">
            742 / <span className="font-light text-lg">12.1%</span>
          </p>
        </motion.div>
      </div>

      <motion.div
        className="w-[60%] h-[84%] drop-shadow-2xl rounded-xl overflow-hidden"
        variants={fadeUpScaleVariant}
        initial="hidden"
        whileInView="visible"
        viewport={{ once: true, amount: 0.3 }}
      >
        <Map/>
      </motion.div>

      <div className="w-[16%] h-[50%] place-items-center flex-col ">
        <motion.div
          className="w-[100%] h-33 bg-white rounded-lg drop-shadow-lg mb-8 px-2 py-2 transition-transform duration-300 hover:scale-105 hover:shadow-2xl "
          variants={fadeUpScaleVariant}
          initial="hidden"
          whileInView="visible"
          viewport={{ once: true, amount: 0.3 }}
        >
          <p className="text-[#3A3A3A] text-base font-normal mb-2">
            Найпопулярніші звернення
          </p>
          <div className="w-[100%] h-[50%] flex items-center gap-3 ">
            <Image
              src="/images/round.svg"
              alt="round"
              width={88}
              height={76}
              unoptimized
              quality={100}
            />
            <ul className="text-[#1E1E1E] text-xs font-medium">
              <li className="flex items-center gap-2">
                <div className="w-2 h-2 bg-[#F1F070] rounded-4xl"></div>
                Благоустрій
              </li>
              <li className="flex items-center gap-2">
                <div className="w-2 h-2 bg-[#CDCDCD] rounded-4xl"></div>Медицина
              </li>
              <li className="flex items-center gap-2">
                <div className="w-2 h-2 bg-[#A6CAEC] rounded-4xl"></div>ЖКГ
              </li>
              <li className="flex items-center gap-2">
                <div className="w-2 h-2 bg-[#F6C6AD] rounded-4xl"></div>Інше
              </li>
            </ul>
          </div>
        </motion.div>

        <motion.div
          className="w-[100%] h-75 bg-white rounded-lg drop-shadow-lg px-4 py-2 transition-transform duration-300 hover:scale-105 hover:shadow-2xl"
          variants={fadeUpScaleVariant}
          initial="hidden"
          whileInView="visible"
          viewport={{ once: true, amount: 0.3 }}
        >
          <p className="text-[#1E1E1E] text-sm font-light">
            Статисткиа робіт за останні
          </p>
          <span className="text-[#1E1E1E] text-sm font-semibold">30 днів</span>
          <div className="w-[100%] place-items-center">
            <Image
              src="/images/Hystogram.png"
              alt="hystogram"
              width={141}
              height={148}
              unoptimized
              quality={100}
            />
          </div>
          <ul className="text-[#1E1E1E] text-xs font-medium">
            <li className="flex items-center gap-2 mb-1 mt-1">
              <div className="bg-[#C2F1C8] rounded-4xl w-2 h-2"></div>Всього
              звернень в роботі
            </li>
            <li className="flex items-center gap-2 mb-1">
              <div className="bg-[#A6CAEC] rounded-4xl w-2 h-2"></div>Всього
              виконаних звернень
            </li>
            <li className="flex items-center gap-2">
              <div className="bg-[#F2CFEE] rounded-4xl w-2 h-2"></div>Всього
              прострочених звернень
            </li>
          </ul>
        </motion.div>
      </div>
    </div>
  );
}
