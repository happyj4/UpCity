import { GoogleMap, OverlayView, useJsApiLoader } from "@react-google-maps/api";
import Link from "next/link";
import { useEffect, useState } from "react";

const containerStyle = {
  width: "100%",
  height: "100%",
};

function Loader({ text = "Завантаження..." }) {
  return (
    <div className="flex bg-[#FBF9F4] flex-col items-center justify-center h-full w-full">
      <div className="border-8 border-t-8 border-gray-200 border-t-orange-500 rounded-full w-16 h-16 animate-spin mb-4"></div>
      <span className="text-gray-700 text-lg">{text}</span>
    </div>
  );
}

export function Map() {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);

  const { isLoaded } = useJsApiLoader({
    googleMapsApiKey: "AIzaSyDmgMdEuinNPIzozKfxgfC1Fw189zH1AWs",
  });

  useEffect(() => {
    const fetchApplications = async () => {
      try {
        const response = await fetch("https://up-city-8xew.vercel.app/appeal", {
          headers: { Accept: "application/json" },
        });
        const data = await response.json();
        const actual = data.filter(
      (item) => item.status !== "Не розглянута",
    );
        setApplications(actual);
      } catch (error) {
        console.error("Помилка завантаження заявок:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchApplications();
  }, []);

  if (!isLoaded) return <Loader text="Завантаження карти…" />;
  if (loading) return <Loader text="Завантаження заявок…" />;
  if (applications.length === 0)
    return (
      <div className="flex items-center justify-center h-full text-gray-500">
        Немає заявок для відображення
      </div>
    );

  const defaultCenter = {
    lat: applications[0]?.latitude || 50.4501,
    lng: applications[0]?.longitude || 30.5234,
  };

  return (
    <div className="w-[65%] bg-[#FBF9F4] h-screen pt-30 relative">
      <GoogleMap mapContainerStyle={containerStyle} center={defaultCenter} zoom={13}>
        {applications.map((app) => {
          const position = { lat: app.latitude, lng: app.longitude };
          const imageUrl = app.image?.image_url || "https://via.placeholder.com/100";

          return (
            <OverlayView
              key={app.application_id}
              position={position}
              mapPaneName={OverlayView.OVERLAY_MOUSE_TARGET}
            >
              <Link
                href={
                  app.status === "В роботі"
                    ? `http://localhost:3000/kp#${app.application_id}`
                    : `http://localhost:3000/kpEnded#${app.application_id}`
                }
              >
                <div
                  className={`w-[60px] h-[60px] rounded-full border-[8px] ${
                    app.status === "В роботі"
                      ? "border-amber-300"
                      : app.status === "Виконано"
                      ? "border-green-500"
                      :  "border border-red-500"
                  } shadow-md overflow-hidden transform -translate-x-1/2 -translate-y-1/2 cursor-pointer`}
                  title={app.name}
                >
                  <img src={imageUrl} alt={app.name} className="w-full h-full object-cover" />
                </div>
              </Link>
            </OverlayView>
          );
        })}
      </GoogleMap>
    </div>
  );
}
