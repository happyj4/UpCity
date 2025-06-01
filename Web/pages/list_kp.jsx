import { List } from "../components/page6/list.jsx";
import { Start } from "../components/page6/start.jsx";
import { Footer } from "../components/WebComponents/footer.jsx";
import { Header } from "../components/WebComponents/headerAdmin.jsx";


export default function () {

  return (
    <div className="h-auto w-screen bg-[#FBF9F4]">
      <Header />
      <Start />
      <List />
      <Footer />
    </div>
  );
}
