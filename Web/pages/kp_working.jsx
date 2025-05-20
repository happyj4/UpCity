import { Inwork } from "../components/page5/inwork.jsx";
import { Footer } from "../components/WebComponents/footer.jsx";
import { Header } from "../components/WebComponents/headerKp.jsx";
import Image from "next/image";

export default function () {
  return (
    <div className="w-screen h-auto bg-[#FBF9F4]">
      <Header />
      <div className=" pt-40">
        <Inwork />
        <Footer />
      </div>
    </div>
  );
}
