package com.example.upcity.page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.upcity.adapters.AdapterAnimation;
import com.example.upcity.helpers.LoadAllApplication;
import com.example.upcity.adapters.AdapterApplication;
import com.example.upcity.R;
import com.example.upcity.adapters.FragmentToolbar;
import com.example.upcity.utils.ResponseApplication;
import java.util.List;

public class AllApplicationPage extends AppCompatActivity {

    private LoadAllApplication loadAllApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_applications);

        Intent intent = getIntent();
        boolean skipAnimation = intent.getBooleanExtra("skipAnimation", false);
        boolean slide_in_left = intent.getBooleanExtra("slide_in_left", false);
        String selectedSortFilter = intent.getStringExtra("selectedSortFilter");
        String selectedDateFilter = intent.getStringExtra("selectedDateFilter");
        String selectedStatusFilter = intent.getStringExtra("selectedStatusFilter");

        if (!skipAnimation) {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);
        }

        if (slide_in_left) {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_left, null, null);
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new FragmentToolbar())
                    .commit();
        }

        // Подключение зависимостей
        RecyclerView AllList = findViewById(R.id.AllList);
        Button HomeButton = findViewById(R.id.HomeButton);
        ImageView OpenFilterButton = findViewById(R.id.OpenFilterButton);

        // Подключение кнопок
        HomeButton.setOnClickListener(view -> {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        });

        HomeButton.setOnClickListener(view -> {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        });

        OpenFilterButton.setOnClickListener(view -> {
            Intent Intent = new Intent(this, FilterPage.class);
            Intent.putExtra("Activity", AllApplicationPage.class.getSimpleName());
            startActivity(Intent);
            overridePendingTransition(R.anim.slide_in_out, 0);
            finish();
        });

        GridLayoutManager gridLayoutManagerAllList = new GridLayoutManager(this, 2);
        AllList.setLayoutManager(gridLayoutManagerAllList);

        // Отображение всех заявок
        loadAllApplication = new LoadAllApplication();
        loadAllApplication.loadApplications(this, selectedSortFilter, selectedDateFilter, selectedStatusFilter, new LoadAllApplication.ApplicationCallback() {
            @Override
            public void onSuccess(List<ResponseApplication> applications) {
                AdapterApplication adapter = new AdapterApplication(applications, false);
                AllList.setAdapter(adapter);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(AllApplicationPage.this, "Ошибка в загрузке всех заявок: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Изменяет кнопку назад
    @Override
    public void onBackPressed() {
        AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
    }
}

