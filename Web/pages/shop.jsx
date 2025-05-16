import { Header } from "../components/page10/Header";
import { Items } from "../components/page10/svg/items";
import { Start } from "../components/page10/svg/start";
import { Footer } from "../components/WebComponents/footer";


export default function(){
    return(
        <div className="w-screen h-auto bg-[#FBF9F4]">
            <Header/>
            <Start/>
            <Items/>
            <Footer/>
        </div>
    )
}