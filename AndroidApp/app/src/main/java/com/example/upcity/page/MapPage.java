package com.example.upcity.page;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.upcity.adapters.AdapterAnimation;
import com.example.upcity.R;
import com.example.upcity.adapters.FragmentToolbar;
import com.example.upcity.helpers.LoadAllApplication;
import com.example.upcity.utils.ResponseApplication;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptor;

import java.util.List;

public class MapPage extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LoadAllApplication loadAllApplication;

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

        loadAllApplication = new LoadAllApplication();
        loadAllApplication.loadApplications(this, new LoadAllApplication.ApplicationCallback() {
            @Override
            public void onSuccess(List<ResponseApplication> applications) {
                for (ResponseApplication app : applications) {
                    if (mMap != null) {
                        LatLng position = new LatLng(app.getLatitude(), app.getLongitude());

                        int iconResId;
                        if (app.getReport() == null) {
                            iconResId = R.drawable.marker_orange;
                        } else if (app.getReport().getImage() == null) {
                            iconResId = R.drawable.marker_gray;
                        } else {
                            iconResId = R.drawable.marker_green;
                        }

                        Marker marker = mMap.addMarker(
                                new MarkerOptions()
                                        .position(position)
                                        .icon(getBitmapDescriptorFromVector(iconResId, 100, 100))
                        );

                        marker.setTag(app);
                    }
                }

                mMap.setOnMarkerClickListener(marker -> {
                    Object tag = marker.getTag();
                    if (tag instanceof ResponseApplication) {
                        ResponseApplication responseApplication = (ResponseApplication) tag;

                        Intent intent = new Intent(MapPage.this, ViewApplicationPage.class);
                        intent.putExtra("applicationId", responseApplication.getApplication_id());
                        intent.putExtra("MapPage", true);

                        AdapterAnimation.animateAndNavigate(
                                MapPage.this,
                                R.id.linearLayout,
                                R.anim.slide_out_left,
                                ViewApplicationPage.class,
                                intent
                        );
                    } else {
                        AdapterAnimation.animateAndNavigate(
                                MapPage.this,
                                R.id.linearLayout,
                                R.anim.slide_out_left,
                                CreateApplicationPage.class,
                                null
                        );
                    }

                    return true;
                });
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(MapPage.this, "Ошибка в загрузке всех заявок: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public BitmapDescriptor getBitmapDescriptorFromVector(int vectorResId, int width, int height) {
        Drawable vectorDrawable = ContextCompat.getDrawable(this, vectorResId);
        if (vectorDrawable == null) {
            return null;
        }
        vectorDrawable.setBounds(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        SharedPreferences prefs = getSharedPreferences("MapPrefs", MODE_PRIVATE);
        double lat = Double.longBitsToDouble(prefs.getLong("lat", Double.doubleToLongBits(49.987324)));
        double lng = Double.longBitsToDouble(prefs.getLong("lng", Double.doubleToLongBits(36.260104)));
        float zoom = prefs.getFloat("zoom", 10.0f);

        LatLng savedLatLng = new LatLng(lat, lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(savedLatLng, zoom));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMap != null) {
            CameraPosition position = mMap.getCameraPosition();
            SharedPreferences.Editor editor = getSharedPreferences("MapPrefs", MODE_PRIVATE).edit();
            editor.putLong("lat", Double.doubleToRawLongBits(position.target.latitude));
            editor.putLong("lng", Double.doubleToRawLongBits(position.target.longitude));
            editor.putFloat("zoom", position.zoom);
            editor.apply();
        }
    }

    //Изменяет кнопку назад
    @Override
    public void onBackPressed() {
        AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
    }
}