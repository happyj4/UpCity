package com.example.upcity.page;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.upcity.adapters.AnimationUtilsHelper;
import com.example.upcity.R;
import com.example.upcity.adapters.ToolbarFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


public class ViewApplicationPage extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private double applicationLatitude;
    private double applicationLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_application);
        AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);
        AnimationUtilsHelper.animateAndNavigate(this, R.id.MaplinearLayout, R.anim.fade_out, null, null);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new ToolbarFragment())
                    .commit();
        }

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
        Button HomeButton = findViewById(R.id.HomeButton);

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

        HomeButton.setOnClickListener(view -> {
            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        });

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng location = new LatLng(applicationLatitude, applicationLongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
        mMap.getUiSettings().setAllGesturesEnabled(false);
    }
}