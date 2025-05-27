import { Inwork } from "../components/page5/inwork.jsx";
import { Footer } from "../components/WebComponents/footer.jsx";
import { HeaderKP} from "../components/WebComponents/headerKp.jsx";


export default function () {
  return (
    <div className="w-screen h-auto bg-[#FBF9F4]">
      <HeaderKP />
      <div className=" pt-40">
        <Inwork />
        <Footer />
      </div>
    </div>
  );
}
