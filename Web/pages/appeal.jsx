import { Header } from "../components/WebComponents/headerAdmin.jsx";
import { Map } from "../components/page3/Map.jsx";
import { Appeals } from "../components/page3/Appeals.jsx";

export default function () {
  return (
    <div className="bg-[#FBF9F4] w-screen h-155">
      <Header />
      <div className="w-screen h-screen flex  bg-[#FBF9F4] px-6">
        <Appeals />
        <Map />
      </div>
    </div>
  );
}
