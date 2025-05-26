package com.example.upcity.page;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upcity.adapters.AdapterAnimation;
import com.example.upcity.helpers.LoadAllApplication;
import com.example.upcity.helpers.LoadUserApplications;
import com.example.upcity.utils.ResponseApplication;
import com.example.upcity.adapters.AdapterApplication;
import com.example.upcity.R;
import com.example.upcity.adapters.FragmentToolbar;

import java.util.List;

public class HomePage extends AppCompatActivity {

    private LoadAllApplication loadAllApplication;
    private LoadUserApplications loadUserApplications;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        boolean skipAnimation = getIntent().getBooleanExtra("skipAnimation", false);

        if (!skipAnimation) {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_left, null, null);
        };

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new FragmentToolbar())
                    .commit();
        }

        // Подключение зависимостей
        RecyclerView MyList = findViewById(R.id.MyList);
        RecyclerView AllList = findViewById(R.id.AllList);
        Button OpenAllButton = findViewById(R.id.OpenAllButton);
        Button OpenMyButton = findViewById(R.id.OpenMyButton);
        ImageButton MapButton = findViewById(R.id.MapButton);

        // Подключение кнопок
        MapButton.setOnClickListener(view -> {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_left, MapPage.class, null);
        });

        OpenAllButton.setOnClickListener(view -> {
            AdapterAnimation.animateAndNavigate(this, R.id.AlllinearLayout, R.anim.slide_out_left, AllApplicationPage.class, null);
        });

        OpenMyButton.setOnClickListener(view -> {
            AdapterAnimation.animateAndNavigate(this, R.id.AlllinearLayout, R.anim.slide_out_left, MyApplicationPage.class, null);
        });

        LinearLayoutManager layoutManagerMyList = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        MyList.setLayoutManager(layoutManagerMyList);

        // Отображение моих заявок
        this.loadUserApplications = new LoadUserApplications();
        LoadUserApplications loadUserApplications = new LoadUserApplications();

        loadUserApplications.getUserApplications(this, null, null, null, new LoadUserApplications.ApplicationCallback() {
            @Override
            public void onSuccess(List<ResponseApplication> applications) {
                AdapterApplication adapter = new AdapterApplication(applications, true);
                MyList.setAdapter(adapter);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(HomePage.this, "Ошибка в загрузке моих заявок: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        GridLayoutManager gridLayoutManagerAllList = new GridLayoutManager(this, 2);
        AllList.setLayoutManager(gridLayoutManagerAllList);

        // Отображение всех заявок
        loadAllApplication = new LoadAllApplication();
        loadAllApplication.loadApplications(this, null, null, null, new LoadAllApplication.ApplicationCallback() {
            @Override
            public void onSuccess(List<ResponseApplication> applications) {
                AdapterApplication adapter = new AdapterApplication(applications, false);
                AllList.setAdapter(adapter);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(HomePage.this, "Ошибка в загрузке всех заявок: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Изменяет кнопку назад
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            if (backToast != null) backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Якщо хочеш вийти, натисни ще раз", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}