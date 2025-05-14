package com.example.upcity.page;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upcity.adapters.AdapterAnimation;
import com.example.upcity.helpers.LoadAllApplication;
import com.example.upcity.adapters.AdapterApplication;
import com.example.upcity.R;
import com.example.upcity.adapters.FragmentToolbar;
import com.example.upcity.utils.RequestCreateApplication;

import java.util.List;

public class AllApplicationPage extends AppCompatActivity {

    private LoadAllApplication loadAllApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_applications);
        AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new FragmentToolbar())
                    .commit();
        }

        // Подключение зависимостей
        RecyclerView AllList = findViewById(R.id.AllList);
        Button HomeButton = findViewById(R.id.HomeButton);

        // Подключение кнопок
        HomeButton.setOnClickListener(view -> {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        });

        GridLayoutManager gridLayoutManagerAllList = new GridLayoutManager(this, 2);
        AllList.setLayoutManager(gridLayoutManagerAllList);

        // Отображение всех заявок
        loadAllApplication = new LoadAllApplication();
        loadAllApplication.loadApplications(this, new LoadAllApplication.ApplicationCallback() {
            @Override
            public void onSuccess(List<RequestCreateApplication> applications) {
                AdapterApplication adapter = new AdapterApplication(applications);
                AllList.setAdapter(adapter);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(AllApplicationPage.this, "Ошибка в загрузке всех заявок: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

