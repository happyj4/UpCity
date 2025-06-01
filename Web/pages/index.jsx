import { Header } from "../components/WebComponents/header.jsx";
import { StartPage } from "../components/page1/PageStart.jsx";
import { MiddlePage } from "../components/page1/MiddlePage.jsx";
import { EndPage } from "../components/page1/EndPage.jsx";
import { Footer } from "../components/WebComponents/footer.jsx";

export default function HomePage() {
  return (
    <div className="w-380 h-auto bg-[#FBF9F4] ">
      <Header />
      <StartPage />
      <MiddlePage />
      <EndPage />
      <Footer />
    </div>
  );
}
