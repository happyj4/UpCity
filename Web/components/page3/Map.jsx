import { useState } from "react";

export function Map() {
  const [loading, setLoading] = useState(true);

  return (
    <div className="w-[65%] h-screen pt-30 relative">
      {loading && (
        <div className="absolute inset-0 z-10 flex justify-center items-center bg-white bg-opacity-70 rounded-2xl">
          {/* Простий CSS-спінер */}
          <div className="w-25 h-25 border-4 border-[#FFBE7D] border-t-transparent rounded-full animate-spin"></div>
        </div>
      )}

      <iframe
        className="w-full h-full rounded-2xl"
        loading="lazy"
        allowFullScreen
        referrerPolicy="no-referrer-when-downgrade"
        onLoad={() => setLoading(false)}
        src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3151.835434509374!2d144.95373531550462!3d-37.81627974202157!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x6ad65d43f2f9f6b3%3A0x2f2ecf0b0a0b4d01!2sFederation%20Square!5e0!3m2!1sen!2sau!4v1616627912439!5m2!1sen!2sau"
      ></iframe>
    </div>
  );
}