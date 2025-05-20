import Image from "next/image";

export function StartPage() {
  return (
    <div className="w-[100%] h-230 flex px-30 py-20 place-content-between pt-50">
      <div className="flex-col w-[45%]">
        <h3 className="text-[#F58E27] text-sm font-medium uppercase drop-shadow-2xl">
          Про нас
        </h3>
        <div className="break-words w-[100%] text-[#1E1E1E] mb-10">
          <p className="uppercase font-semibold text-6xl mb-10">
            upcity — інноваційний підхід
          </p>
          <p className="text-2xl font-light">
            Ми прагнемо зробити міста більш комфортними, безпечними та
            сучасними. Наша команда працює над створенням ефективних рішень для
            мешканців, враховуючи їх потреби та побажання.
          </p>
        </div>
        <Image
          src="/images/Photo1.png"
          alt="redminote"
          width={579}
          height={407}
          unoptimized
        />
      </div>
      <div className="flex-col w-[45%] break-words text-[#1E1E1E] text-xl font-light">
        <Image
          src="/images/Photo2.png"
          alt="redminote"
          width={568}
          height={704}
          unoptimized
        />
        <p className="mt-10">
          UpCity — це платформа для взаємодії між громадянами, експертами та
          міською владою. Ми віримо, що тільки спільними зусиллями можна зробити
          наше середовище кращим для життя.
        </p>
      </div>
    </div>
  );
}
