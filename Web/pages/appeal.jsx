import { Headeradmin } from "../components/WebComponents/headerAdmin.jsx";
import { Map } from "../components/page3/Map.jsx";
import { Appeals } from "../components/page3/Appeals.jsx";
import { Header } from "../components/WebComponents/header.jsx";
import { useState, useEffect } from "react";

export default function () {
  const [check, setCheck] = useState("")
 useEffect(() => {
    const isAdmin = sessionStorage.getItem("Admin");
    if (isAdmin) {
      setCheck("admin");
    } else {
      setCheck("");
    }
  }, []);
  
  return (
    <div className="bg-[#FBF9F4] w-screen h-[90%]">
      {check? <Headeradmin/>: <Header/>}
      <div className="w-screen h-screen flex  bg-[#FBF9F4] px-6">
        <Appeals />
        <Map />
      </div>
    </div>
  );
}
