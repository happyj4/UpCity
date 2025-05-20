package com.example.upcity.page;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import androidx.appcompat.app.AppCompatActivity;
import com.example.upcity.R;
import com.example.upcity.adapters.AdapterAnimation;
import com.example.upcity.adapters.FragmentToolbar;
import com.example.upcity.helpers.LoadApplicationInfo;
import com.example.upcity.utils.ResponseDetailsApplication;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ViewApplicationPage extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double applicationLatitude;
    private double applicationLongitude;

    private TextView IdApplicationText;
    private TextView NameApplicationText;
    private TextView AddressApplicationText;
    private TextView KpApplicationText;
    private TextView DescriptionApplicationText;
    private TextView DateApplicationText;
    private ImageView сlientPhoto;
    private ImageView statusApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_application);

        AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);
        AdapterAnimation.animateAndNavigate(this, R.id.MaplinearLayout, R.anim.fade_out, null, null);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new FragmentToolbar())
                    .commit();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        Button HomeButton = findViewById(R.id.HomeButton);
        HomeButton.setOnClickListener(view -> {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        });

        // Подключение зависимостей
        IdApplicationText = findViewById(R.id.IdApplicationText);
        NameApplicationText = findViewById(R.id.NameApplicationText);
        AddressApplicationText = findViewById(R.id.AddressApplicationText);
        KpApplicationText = findViewById(R.id.KpApplicationText);
        DescriptionApplicationText = findViewById(R.id.DescriptionApplicationText);
        DateApplicationText = findViewById(R.id.DateApplicationText);
        сlientPhoto = findViewById(R.id.ClientPhoto);
        statusApplication = findViewById(R.id.StatusApplication);

        // Полчайем ID заявки
        Intent intent = getIntent();
        int applicationId = intent.getIntExtra("applicationId", -1);

        if (applicationId != -1) {
            loadApplicationDetails(applicationId);
        } else {
            Toast.makeText(this, "ID заявки не найден", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadApplicationDetails(int appId) {
        SharedPreferences prefs = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access_token", null);

        LoadApplicationInfo.loadApplicationDetails(this, appId, accessToken, new LoadApplicationInfo.ApplicationDetailsCallback() {
            @Override
            public void onApplicationDetailsLoaded(ResponseDetailsApplication app) {
                IdApplicationText.setText("#" + app.getApplicationId());
                NameApplicationText.setText(app.getName());
                AddressApplicationText.setText(app.getAddress());
                KpApplicationText.setText(app.getUtilityCompany().getName());
                DescriptionApplicationText.setText(app.getDescription());

                if (app.getStatus().equals("Виконано")) {
                    statusApplication.setImageResource(R.drawable.completed_application);
                } else if (app.getStatus().equals("В роботі")) {
                    statusApplication.setImageResource(R.drawable.work_application);
                } else {
                    statusApplication.setImageResource(R.drawable.rejected_application);
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                DateApplicationText.setText(dateFormat.format(app.getApplicationDate()));

                String imageUrl = app.getImage().getImageUrl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Glide.with(ViewApplicationPage.this)
                            .load(imageUrl)
                            .into(сlientPhoto);
                }

                applicationLatitude = app.getLatitude();
                applicationLongitude = app.getLongitude();

                if (mMap != null) {
                    updateMap();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(ViewApplicationPage.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateMap() {
        LatLng location = new LatLng(applicationLatitude, applicationLongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
        mMap.getUiSettings().setAllGesturesEnabled(false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (applicationLatitude != 0 && applicationLongitude != 0) {
            updateMap();
        }
    }
}
