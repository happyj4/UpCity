package com.example.upcity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.imageview.ShapeableImageView;

import org.w3c.dom.Text;


public class ViewApplicationPage extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private double applicationLatitude;
    private double applicationLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_application_page);
        addToolbar();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        TextView IdApplicationText = findViewById(R.id.IdApplicationText);
        TextView NameApplicationText = findViewById(R.id.NameApplicationText);
        TextView AddressApplicationText = findViewById(R.id.AddressApplicationText);
        TextView KpApplicationText = findViewById(R.id.KpApplicationText);
        TextView DescriptionApplicationText = findViewById(R.id.DescriptionApplicationText);
        TextView DateApplicationText = findViewById(R.id.DateApplicationText);

        Intent intent = getIntent();
        int applicationId = intent.getIntExtra("applicationId", -1);
        String applicationName = intent.getStringExtra("applicationName");
        String applicationDescription = intent.getStringExtra("applicationDescription");
        String applicationAddress = intent.getStringExtra("applicationAdress");
        String applicationDate = intent.getStringExtra("applicationDate");
        int applicationKpId = intent.getIntExtra("applicationKpId", -1);
        String applicationStatus = intent.getStringExtra("applicationStatus");
        int applicationImageId = intent.getIntExtra("applicationImageId", -1);
        applicationLatitude = intent.getDoubleExtra("applicationLatitude", 0.0);
        applicationLongitude = intent.getDoubleExtra("applicationLongitude", 0.0);

        IdApplicationText.setText(String.valueOf(applicationId));
        NameApplicationText.setText(applicationName);
        AddressApplicationText.setText(applicationAddress);
        KpApplicationText.setText(String.valueOf(applicationKpId));
        DescriptionApplicationText.setText(applicationDescription);
        DateApplicationText.setText(applicationDate);
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng location = new LatLng(applicationLatitude, applicationLongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
        mMap.getUiSettings().setAllGesturesEnabled(false);
    }

    public void addToolbar() {
        Menu menu;

        ImageButton PlusButton = findViewById(R.id.PlusButton);
        ShapeableImageView PhotoButton = findViewById(R.id.PhotoButton);
        Button HomeButton = findViewById(R.id.HomeButton);

        menu = new Menu();
        PhotoButton.setOnClickListener(view -> menu.showPopupMenu(view, ViewApplicationPage.this));

        PlusButton.setOnClickListener(view -> {
            Intent intent = new Intent(ViewApplicationPage.this, EditProfilePage.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        HomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(ViewApplicationPage.this, HomePage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, 0);
            finish();
        });
    }
}