package com.example.upcity.page;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upcity.adapters.AnimationUtilsHelper;
import com.example.upcity.utils.Application;
import com.example.upcity.adapters.ApplicationAdapter;
import com.example.upcity.R;
import com.example.upcity.adapters.ToolbarFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        boolean skipAnimation = getIntent().getBooleanExtra("skipAnimation", false);

        if (!skipAnimation) {
            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_left, null, null);
        };

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new ToolbarFragment())
                    .commit();
        }

        RecyclerView MyList = findViewById(R.id.MyList);
        RecyclerView AllList = findViewById(R.id.AllList);
        Button OpenAllButton = findViewById(R.id.OpenAllButton);
        Button OpenMyButton = findViewById(R.id.OpenMyButton);
        ImageButton MapButton = findViewById(R.id.MapButton);

        MapButton.setOnClickListener(view -> {
            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_left, MapPage.class, null);
        });

        OpenAllButton.setOnClickListener(view -> {
            AnimationUtilsHelper.animateAndNavigate(this, R.id.AlllinearLayout, R.anim.slide_out_left, AllApplicationPage.class, null);
        });

        OpenMyButton.setOnClickListener(view -> {
            AnimationUtilsHelper.animateAndNavigate(this, R.id.AlllinearLayout, R.anim.slide_out_left, MyApplicationPage.class, null);
        });

        LinearLayoutManager layoutManagerMyList = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        MyList.setLayoutManager(layoutManagerMyList);

        GridLayoutManager gridLayoutManagerAllList = new GridLayoutManager(this, 2);
        AllList.setLayoutManager(gridLayoutManagerAllList);

        List<Application> applicationList = new ArrayList<>();
        applicationList.add(new Application(1, "Заява 1", "Опис заяви", "Адреса заяви", 123, "01.01.2022"));
        applicationList.add(new Application(2, "Заява 2", "Опис заяви", "Адреса заяви", 123, "01.01.2023"));
        applicationList.add(new Application(3, "Заява 3", "Опис заяви", "Адреса заяви", 123, "01.01.2024"));

        ApplicationAdapter adapter = new ApplicationAdapter(applicationList);

        MyList.setAdapter(adapter);
        AllList.setAdapter(adapter);

/*
        ApiService apiService = RetrofitClient.getInstance();

        apiService.getMessage().enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("FastAPI", "Ответ: " + response.body().get("message"));
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Log.e("FastAPI", "Ошибка: " + t.getMessage());
            }
        });
*/
    }
}