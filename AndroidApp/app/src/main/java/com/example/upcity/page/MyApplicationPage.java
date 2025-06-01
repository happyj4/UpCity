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
import com.example.upcity.helpers.LoadUserApplications;
import com.example.upcity.utils.ResponseApplication;
import com.example.upcity.adapters.AdapterApplication;
import com.example.upcity.R;
import com.example.upcity.adapters.FragmentToolbar;

import java.util.List;

public class MyApplicationPage extends AppCompatActivity {

    private LoadUserApplications loadUserApplications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_applications);

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

        RecyclerView AllList = findViewById(R.id.AllList);
        Button HomeButton = findViewById(R.id.HomeButton);
        ImageView OpenFilterButton = findViewById(R.id.OpenFilterButton);

        HomeButton.setOnClickListener(view -> {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        });

        OpenFilterButton.setOnClickListener(view -> {
            Intent Intent = new Intent(this, FilterPage.class);
            Intent.putExtra("Activity", MyApplicationPage.class.getSimpleName());
            startActivity(Intent);
            overridePendingTransition(R.anim.slide_in_out, 0);
            finish();
        });

        GridLayoutManager gridLayoutManagerAllList = new GridLayoutManager(this, 2);
        AllList.setLayoutManager(gridLayoutManagerAllList);

        this.loadUserApplications = new LoadUserApplications();
        LoadUserApplications loadUserApplications = new LoadUserApplications();

        loadUserApplications.getUserApplications(this, selectedSortFilter, selectedDateFilter, selectedStatusFilter, new LoadUserApplications.ApplicationCallback() {
            @Override
            public void onSuccess(List<ResponseApplication> applications) {
                List<ResponseApplication> applicationList = applications;
                applications.add(0, null);
                AdapterApplication adapter = new AdapterApplication(applicationList, false);
                AllList.setAdapter(adapter);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(MyApplicationPage.this, "Ошибка в загрузке моих заявок: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Изменяет кнопку назад
    @Override
    public void onBackPressed() {
        AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
    }
}