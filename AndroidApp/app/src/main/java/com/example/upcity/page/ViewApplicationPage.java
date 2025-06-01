package com.example.upcity.page;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
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
    private LinearLayout UtilityCompanieInfo;
    private FrameLayout ClientPhotoFrame;
    private FrameLayout UtilityCompaniePhotoFrame;
    private Button DeleteApplicationButton;
    private int applicationId;
    private String activity;

    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_application);

        boolean skipAnimation = getIntent().getBooleanExtra("skipAnimation", false);
        activity = getIntent().getStringExtra("activity");

        if (!skipAnimation) {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);
        };

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
        DeleteApplicationButton = findViewById(R.id.DeleteApplicationButton);
        ClientPhotoFrame = findViewById(R.id.ClientPhotoFrame);
        UtilityCompaniePhotoFrame = findViewById(R.id.UtilityCompaniePhotoFrame);

        star1 = findViewById(R.id.Star1);
        star2 = findViewById(R.id.Star2);
        star3 = findViewById(R.id.Star3);
        star4 = findViewById(R.id.Star4);
        star5 = findViewById(R.id.Star5);

        // Подключение кнопок
        HomeButton.setOnClickListener(view -> {
            try {
                String className = "com.example.upcity.page." + activity;
                Class<?> clazz = Class.forName(className);
                Intent Intent = new Intent(this, clazz);
                Intent.putExtra("slide_in_left", true);
                Intent.putExtra("applicationId", applicationId);
                AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, clazz, Intent);
            } catch (ClassNotFoundException e) {
                Intent Intent = new Intent(this, HomePage.class);
                Intent.putExtra("applicationId", applicationId);
                AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, Intent);
            }
        });

        ClientPhoto.setOnClickListener(view -> {
            onPhotoClick(ClientPhotoFrame);
        });

        UtilityCompaniePhoto.setOnClickListener(view -> {
            onPhotoClick(UtilityCompaniePhotoFrame);
        });

        DeleteApplicationButton.setOnClickListener(view -> {
            Intent Intent = new Intent(this, DeleteApplicationPage.class);
            Intent.putExtra("applicationId", applicationId);
            Intent.putExtra("activity", activity);
            startActivity(Intent);
            overridePendingTransition(R.anim.slide_in_out, 0);
            finish();
        });

        // Полчайем ID заявки
        Intent intent = getIntent();
        applicationId = intent.getIntExtra("applicationId", -1);

        if (applicationId != -1) {
            loadApplicationDetails(applicationId);
        } else {
            Toast.makeText(this, "ID заявки не найден", Toast.LENGTH_SHORT).show();
        }
    }

    //Показ деталей заявки
    private void loadApplicationDetails(int appId) {
        SharedPreferences prefs = this.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access_token", null);
        String userId = getUserIdFromToken(accessToken);

        LoadApplicationInfo.loadApplicationDetails(this, appId, new LoadApplicationInfo.ApplicationDetailsCallback() {
            @Override
            public void onApplicationDetailsLoaded(ResponseApplication responseApplication) {
                IdApplicationText.setText("#" + responseApplication.getApplication_id());
                NameApplicationText.setText(responseApplication.getName());
                AddressApplicationText.setText(responseApplication.getAddress());
                KpApplicationText.setText(responseApplication.getUtility_company().getName());
                DescriptionApplicationText.setText(responseApplication.getDescription());

                if (responseApplication.getStatus().equals("Виконано")) {
                    DeleteApplicationButton.setVisibility(View.GONE);
                    StatusApplication.setImageResource(R.drawable.status_complete);
                } else if (responseApplication.getStatus().equals("В роботі")) {
                    DeleteApplicationButton.setVisibility(View.GONE);
                    StatusApplication.setImageResource(R.drawable.status_work);
                    UtilityCompanieInfo.setVisibility(View.GONE);
                } else if (responseApplication.getStatus().equals("Не розглянута")) {
                    StatusApplication.setImageResource(R.drawable.status_waiting);
                    UtilityCompanieInfo.setVisibility(View.GONE);

                    if (userId != null && !userId.equals(String.valueOf(responseApplication.getUser().getUser_id()))) {
                        DeleteApplicationButton.setVisibility(View.GONE);
                    }
                } else {
                    DeleteApplicationButton.setVisibility(View.GONE);
                    UtilityCompanieInfo.setVisibility(View.GONE);
                    StatusApplication.setImageResource(R.drawable.status_rejected);
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                DateApplicationText.setText(dateFormat.format(responseApplication.getApplication_date()));

                String imageUrl = responseApplication.getImage().getImage_url();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Glide.with(ViewApplicationPage.this)
                            .load(imageUrl)
                            .into(ClientPhoto);
                }

                if (responseApplication.getReport() != null) {
                    ResponseApplication.Image image = responseApplication.getReport().getImage();
                    if (image != null) {
                        String imageUtilityCompanieUrl = image.getImage_url();
                        if (imageUtilityCompanieUrl != null && !imageUtilityCompanieUrl.isEmpty()) {
                            Glide.with(ViewApplicationPage.this)
                                    .load(imageUtilityCompanieUrl)
                                    .into(UtilityCompaniePhoto);

                            ImageView[] stars = {star1, star2, star3, star4, star5};
                            int rating = responseApplication.getUser_rating();

                            for (int i = 0; i < stars.length; i++) {
                                if (i < rating) {
                                    stars[i].setColorFilter(Color.parseColor("#FFE68C"));
                                } else {
                                    stars[i].setColorFilter(Color.parseColor("#BCBCBC"));
                                }
                            }
                        }
                    }
                }

                applicationLatitude = responseApplication.getLatitude();
                applicationLongitude = responseApplication.getLongitude();

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

    // Получение id с токена
    public static String getUserIdFromToken(String jwtToken) {
        try {
            String[] parts = jwtToken.split("\\.");
            if (parts.length < 2) return null;

            String payload = new String(Base64.decode(parts[1], Base64.URL_SAFE), StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(payload);
            return jsonObject.getString("sub");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Обновление карты
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

    // Увеличение фотки при нажатии
    public void onPhotoClick(FrameLayout frameLayout) {
        float density = frameLayout.getResources().getDisplayMetrics().density;
        int collapsedHeight = (int)(132 * density);
        int expandedHeight = (int)(300 * density);

        boolean isExpanded = frameLayout.getTag() != null && (boolean) frameLayout.getTag();
        int startHeight = frameLayout.getHeight();
        int endHeight = isExpanded ? collapsedHeight : expandedHeight;

        ValueAnimator animator = ValueAnimator.ofInt(startHeight, endHeight);
        animator.setDuration(300);
        animator.addUpdateListener(animation -> {
            int val = (Integer) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
            layoutParams.height = val;
            frameLayout.setLayoutParams(layoutParams);
        });
        animator.start();
        frameLayout.setTag(!isExpanded);
    }

    //Изменяет кнопку назад
    @Override
    public void onBackPressed() {
        try {
            String className = "com.example.upcity.page." + activity;
            Class<?> clazz = Class.forName(className);
            Intent Intent = new Intent(this, clazz);
            Intent.putExtra("slide_in_left", true);
            Intent.putExtra("applicationId", applicationId);
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, clazz, Intent);
        } catch (ClassNotFoundException e) {
            Intent Intent = new Intent(this, HomePage.class);
            Intent.putExtra("applicationId", applicationId);
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, Intent);
        }
    }
}
