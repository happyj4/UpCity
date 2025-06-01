import { Header } from "../components/WebComponents/header.jsx";
import { StartPage } from "../components/page2/PageStart.jsx";
import { MiddlePage } from "../components/page2/MiddlePage.jsx";
import { EndPage } from "../components/page2/EndPage.jsx";
import { Footer } from "../components/WebComponents/footer.jsx";

export default function () {
  return (
    <div className="w-screen h-760 bg-[#FBF9F4]">
      <Header />
      <StartPage />
      <MiddlePage />
      <EndPage />
      <Footer />
    </div>
  );
}
