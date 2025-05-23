import { GoogleMap, OverlayView, useJsApiLoader } from "@react-google-maps/api";
import { useState, useEffect } from "react";

const containerStyle = {
  width: "100%",
  height: "100%",
};

export function Map() {
  const [info, setInfo] = useState(null);
  const center = info
    ? { lat: info.latitude, lng: info.longitude }
    : { lat: 0, lng: 0 };

  useEffect(() => {
    const token = sessionStorage.getItem("access_token");
    const id = window.location.hash?.replace("#", "") || null;
    if (id) {
      fetch(`http://46.101.245.42/application/${id}`, {
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

  if (!isLoaded) return <div>Loading...</div>;
  if (!info) return <div className="w-[65%] h-full bg-gray-100 rounded-2xl" />;

  const imageUrl = info.image?.image_url || "https://via.placeholder.com/100";

  return (
    <GoogleMap mapContainerStyle={containerStyle} center={center} zoom={15}>
      <OverlayView
        position={center}
        mapPaneName={OverlayView.OVERLAY_MOUSE_TARGET}
      >
        <div
          style={{
            width: 60,
            height: 60,
            borderRadius: "50%",
            border: "3px solid red",
            boxShadow: "0 0 10px rgba(0,0,0,0.3)",
            overflow: "hidden",
            transform: "translate(-50%, -50%)", // Центрує маркер
          }}
        >
          <img
            src={imageUrl}
            alt="Маркер"
            style={{ width: "100%", height: "100%", objectFit: "cover" }}
          />
        </div>
      </OverlayView>
    </GoogleMap>
  );
}
