import { motion } from "framer-motion";

export function Start() {
  return (
    <div className="px-25 pt-45">
      <motion.h1
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
        className="text-[#3A3A3A] text-4xl font-semibold mb-10"
      >
        Список комунальних підприємств
      </motion.h1>
    </div>
  );
}
