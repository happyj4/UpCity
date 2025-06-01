import dynamic from "next/dynamic";
import { Headeradmin } from "../components/WebComponents/headerAdmin";

const Edit = dynamic(() => import("../components/page7/edit"), {
  ssr: false,
});

export default function EditPage() {
  return (
    <div className="w-screen h-screen bg-[#FBF9F4]">
      <Headeradmin />
      <Edit />
    </div>
  );
}
