import { GoogleMap, OverlayView, useJsApiLoader } from "@react-google-maps/api";
import { useState, useEffect } from "react";

function Loader({ text = "Завантаження..." }) {
  return (
    <div className="flex flex-col items-center justify-center h-full w-full">
      <div className="border-8 border-t-8 border-gray-200 border-t-orange-500 rounded-full w-16 h-16 animate-spin mb-4"></div>
      <span className="text-gray-700 text-lg">{text}</span>
    </div>
  );
}

export function Map() {
  const [info, setInfo] = useState(null);

  const center = info
    ? { lat: info.latitude, lng: info.longitude }
    : { lat: 0, lng: 0 };

  useEffect(() => {
    const token = sessionStorage.getItem("access_token");
    const id = window.location.hash?.replace("#", "") || null;
    if (id) {
      fetch(`https://upcity.live/application/${id}`, {
        headers: {
          Accept: "application/json",
          Authorization: `Bearer ${token}`,
        },
      })
        .then((res) => res.json())
        .then((data) => setInfo(data))
        .catch(console.error);
    }
  }, []);

  const { isLoaded } = useJsApiLoader({
    googleMapsApiKey: "AIzaSyDmgMdEuinNPIzozKfxgfC1Fw189zH1AWs",
  });

  if (!isLoaded) return <Loader text="Завантаження карти…" />;

  if (!info) return <Loader text="Завантаження заявки…" />;

  const imageUrl = info.image?.image_url || "https://via.placeholder.com/100";

  return (
    <GoogleMap
      mapContainerClassName="w-full h-full" // Tailwind стилі замість containerStyle
      center={center}
      zoom={15}
    >
      <OverlayView
        position={center}
        mapPaneName={OverlayView.OVERLAY_MOUSE_TARGET}
      >
        <div
          className={`w-[60px] h-[60px] rounded-full border-8 ${
            info.status === "В роботі"
              ? "border-amber-300"
              : info.status === "Виконано"
              ? "border-green-500"
              : info.status === "Відхилено"
              ? "border-red-500" : "border-gray-600"
          } shadow-md overflow-hidden transform -translate-x-1/2 -translate-y-1/2`}
        >
          <img
            src={imageUrl}
            alt="Маркер"
            className="w-full h-full object-cover"
          />
        </div>
      </OverlayView>
    </GoogleMap>
  );
}
