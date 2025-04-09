package com.example.upcity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AllApplicationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_application_page);
        AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new ToolbarFragment())
                    .commit();
        }

        RecyclerView AllList = findViewById(R.id.AllList);
        Button HomeButton = findViewById(R.id.HomeButton);

        HomeButton.setOnClickListener(view -> {
            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        });

        GridLayoutManager gridLayoutManagerAllList = new GridLayoutManager(this, 2);
        AllList.setLayoutManager(gridLayoutManagerAllList);

        List<Application> applicationList = new ArrayList<>();
        applicationList.add(new Application(1, "Заява 1", "Опис заяви", "Адреса заяви", 123, "01.01.2022"));
        applicationList.add(new Application(2, "Заява 2", "Опис заяви", "Адреса заяви", 123, "01.01.2023"));
        applicationList.add(new Application(3, "Заява 3", "Опис заяви", "Адреса заяви", 123, "01.01.2024"));

        ApplicationAdapter adapter = new ApplicationAdapter(applicationList);

        AllList.setAdapter(adapter);
    }
}