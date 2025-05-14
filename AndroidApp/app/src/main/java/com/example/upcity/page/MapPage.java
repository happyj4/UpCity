package com.example.upcity.page;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.example.upcity.adapters.AdapterAnimation;
import com.example.upcity.R;
import com.example.upcity.adapters.FragmentToolbar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


public class MapPage extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);
        AdapterAnimation.animateAndNavigate(this, R.id.MaplinearLayout, R.anim.fade_out, null, null);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new FragmentToolbar())
                    .commit();
        }

        // Подключение зависимостей
        ImageButton ListButton = findViewById(R.id.ListButton);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Подключение кнопок
        ListButton.setOnClickListener(view -> {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        });
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng location = new LatLng(49.987324, 36.260104);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
    }
}