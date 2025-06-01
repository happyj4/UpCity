import { List } from "../components/page6/list.jsx";
import { Start } from "../components/page6/start.jsx";
import { Footer } from "../components/WebComponents/footerAdmin.jsx";
import { Headeradmin } from "../components/WebComponents/headerAdmin.jsx";


export default function () {

  return (
    <div className="h-auto w-screen bg-[#FBF9F4]">
      <Headeradmin />
      <Start />
      <List />
      <Footer />
    </div>
  );
}
