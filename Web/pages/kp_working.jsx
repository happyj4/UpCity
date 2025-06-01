import { Inwork } from "../components/page5/inwork.jsx";
import { Footer } from "../components/WebComponents/footerKp.jsx";
import { HeaderKP} from "../components/WebComponents/headerKp.jsx";


export default function () {
  return (
    <div className="w-screen h-auto bg-[#FBF9F4]">
      <HeaderKP />
      <div className=" w-full">
        <Inwork />
        <Footer />
      </div>
    </div>
  );
}
