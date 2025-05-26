package com.example.upcity.page;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import androidx.appcompat.app.AppCompatActivity;
import com.example.upcity.R;
import com.example.upcity.adapters.AdapterAnimation;
import com.example.upcity.adapters.FragmentToolbar;
import com.example.upcity.helpers.LoadApplicationInfo;
import com.example.upcity.utils.ResponseApplication;
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

    Button HomeButton;
    private TextView IdApplicationText;
    private TextView NameApplicationText;
    private TextView AddressApplicationText;
    private TextView KpApplicationText;
    private TextView DescriptionApplicationText;
    private TextView DateApplicationText;
    private ImageView ClientPhoto;
    private ImageView StatusApplication;
    private ImageView UtilityCompaniePhoto;
    private boolean MapPage;
    private LinearLayout UtilityCompanieInfo;
    private FrameLayout ClientPhotoFrame;
    private FrameLayout UtilityCompaniePhotoFrame;


    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;

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

        // Подключение зависимостей
        HomeButton = findViewById(R.id.HomeButton);
        IdApplicationText = findViewById(R.id.IdApplicationText);
        NameApplicationText = findViewById(R.id.NameApplicationText);
        AddressApplicationText = findViewById(R.id.AddressApplicationText);
        KpApplicationText = findViewById(R.id.KpApplicationText);
        DescriptionApplicationText = findViewById(R.id.DescriptionApplicationText);
        DateApplicationText = findViewById(R.id.DateApplicationText);
        ClientPhoto = findViewById(R.id.ClientPhoto);
        UtilityCompaniePhoto = findViewById(R.id.UtilityCompaniePhoto);
        StatusApplication = findViewById(R.id.StatusApplication);
        UtilityCompanieInfo = findViewById(R.id.UtilityCompanieInfo);

        ClientPhotoFrame = findViewById(R.id.ClientPhotoFrame);
        UtilityCompaniePhotoFrame = findViewById(R.id.UtilityCompaniePhotoFrame);

        star1 = findViewById(R.id.Star1);
        star2 = findViewById(R.id.Star2);
        star3 = findViewById(R.id.Star3);
        star4 = findViewById(R.id.Star4);
        star5 = findViewById(R.id.Star5);

        // Подключение КНОПОК
        HomeButton.setOnClickListener(view -> {
            if (MapPage == true) {
                AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, MapPage.class, null);
            }
            else {
                AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
            }
        });

        ClientPhoto.setOnClickListener(view -> {
            onPhotoClick(ClientPhotoFrame);
        });

        UtilityCompaniePhoto.setOnClickListener(view -> {
            onPhotoClick(UtilityCompaniePhotoFrame);
        });

        // Полчайем ID заявки
        Intent intent = getIntent();
        int applicationId = intent.getIntExtra("applicationId", -1);
        MapPage = intent.getBooleanExtra("MapPage", false);

        if (applicationId != -1) {
            loadApplicationDetails(applicationId);
        } else {
            Toast.makeText(this, "ID заявки не найден", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadApplicationDetails(int appId) {
        LoadApplicationInfo.loadApplicationDetails(this, appId, new LoadApplicationInfo.ApplicationDetailsCallback() {
            @Override
            public void onApplicationDetailsLoaded(ResponseApplication app) {
                IdApplicationText.setText("#" + app.getApplication_id());
                NameApplicationText.setText(app.getName());
                AddressApplicationText.setText(app.getAddress());
                KpApplicationText.setText(app.getUtility_company().getName());
                DescriptionApplicationText.setText(app.getDescription());

                if (app.getStatus().equals("Виконано")) {
                    StatusApplication.setImageResource(R.drawable.completed_application);
                } else if (app.getStatus().equals("В роботі")) {
                    StatusApplication.setImageResource(R.drawable.work_application);
                } else {
                    StatusApplication.setImageResource(R.drawable.rejected_application);
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                DateApplicationText.setText(dateFormat.format(app.getApplication_date()));

                String imageUrl = app.getImage().getImage_url();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Glide.with(ViewApplicationPage.this)
                            .load(imageUrl)
                            .into(ClientPhoto);
                }

                if (app.getReport() != null) {
                    ResponseApplication.Image image = app.getReport().getImage();
                    if (image != null) {
                        String imageUtilityCompanieUrl = image.getImage_url();
                        if (imageUtilityCompanieUrl != null && !imageUtilityCompanieUrl.isEmpty()) {
                            Glide.with(ViewApplicationPage.this)
                                    .load(imageUtilityCompanieUrl)
                                    .into(UtilityCompaniePhoto);

                            ImageView[] stars = {star1, star2, star3, star4, star5};
                            int rating = app.getUser_rating();

                            for (int i = 0; i < stars.length; i++) {
                                if (i < rating) {
                                    stars[i].setColorFilter(Color.parseColor("#FFE68C"));
                                } else {
                                    stars[i].setColorFilter(Color.parseColor("#BCBCBC"));
                                }
                            }
                        }
                    } else {
                        UtilityCompanieInfo.setVisibility(View.GONE);
                    }
                } else {
                    UtilityCompanieInfo.setVisibility(View.GONE);
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

    public void onPhotoClick(FrameLayout frameLayout) {
        int startHeight = frameLayout.getHeight();
        float density = frameLayout.getResources().getDisplayMetrics().density;
        int collapsedHeight = (int)(132 * density);
        int expandedHeight = (int)(300 * density);
        int endHeight = (startHeight == collapsedHeight) ? expandedHeight : collapsedHeight;

        ValueAnimator animator = ValueAnimator.ofInt(startHeight, endHeight);
        animator.setDuration(300);
        animator.addUpdateListener(animation -> {
            int val = (Integer) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
            layoutParams.height = val;
            frameLayout.setLayoutParams(layoutParams);
        });
        animator.start();
    }


    //Изменяет кнопку назад
    @Override
    public void onBackPressed() {
        AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
    }
}
