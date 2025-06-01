import { HeaderKP } from "../components/WebComponents/headerKp.jsx";
import { Map } from "../components/page4/Map.jsx";
import { KpAppeals } from "../components/page4/KpAppealsEnded.jsx";
import { Header } from "../components/WebComponents/header.jsx";
import { useState, useEffect } from "react";
import { Headeradmin } from "../components/WebComponents/headerAdmin.jsx";

export default function () {
const [check, setCheck] = useState("")
 useEffect(() => {
    const isKP = sessionStorage.getItem("KP");
    const isAdmin = sessionStorage.getItem("Admin");
    if (isKP) {
      setCheck("КП");
    } else if(isAdmin){
      setCheck("admin");
    }else{
      setCheck("");
    }
  }, []);

  return (
    <div className="bg-[#FBF9F4] w-screen h-screen">
      {check === "КП" ? <HeaderKP/> : check === "admin" ? <Headeradmin/> : <Header/>}
      <div className="w-screen h-full flex pt-30 bg-[#FBF9F4] px-6">
        <KpAppeals />
        <Map />
      </div>
    </div>
  );
}
