package com.example.upcity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.imageview.ShapeableImageView;


public class MapPage extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_page);
        addToolbar();

        ImageButton ListButton = findViewById(R.id.ListButton);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        ListButton.setOnClickListener(view -> {
            Intent intent = new Intent(MapPage.this, HomePage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, 0);
            finish();
        });
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng location = new LatLng(49.987324, 36.260104);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
    }

    public void addToolbar() {
        Menu menu;

        ImageButton PlusButton = findViewById(R.id.PlusButton);
        ShapeableImageView PhotoButton = findViewById(R.id.PhotoButton);

        menu = new Menu();
        PhotoButton.setOnClickListener(view -> menu.showPopupMenu(view, MapPage.this));


        PlusButton.setOnClickListener(view -> {
            Intent intent = new Intent(MapPage.this, CreateApplicationPage.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
    }
}