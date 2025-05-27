import { Search } from "../components/page9/search.jsx";
import { Footer } from "../components/WebComponents/footer.jsx";
import { Headeradmin } from "../components/WebComponents/headerAdmin.jsx";

export default function () {
  return (
    <div className="h-auto w-screen bg-[#FBF9F4]">
      <Headeradmin />
      <Search />
      <div className="mb-50"></div>
      <Footer />
    </div>
  );
}
