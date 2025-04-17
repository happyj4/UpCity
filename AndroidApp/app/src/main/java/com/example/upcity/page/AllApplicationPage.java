package com.example.upcity.page;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upcity.adapters.AnimationUtilsHelper;
import com.example.upcity.utils.Application;
import com.example.upcity.adapters.ApplicationAdapter;
import com.example.upcity.R;
import com.example.upcity.adapters.ToolbarFragment;

import java.util.ArrayList;
import java.util.List;

public class AllApplicationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_applications);
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