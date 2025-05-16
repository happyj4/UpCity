import { Header } from "../components/WebComponents/header.jsx";
import { Map } from "../components/page4/Map.jsx";
import { KpAppeals } from "../components/page4/KpAppeals.jsx";

export default function () {
  return (
    <div className="bg-[#FBF9F4] w-screen h-screen">
      <Header />
      <div className="w-screen h-full flex pt-30 bg-[#FBF9F4] px-6">
        <KpAppeals />
        <Map />
      </div>
    </div>
  );
}
