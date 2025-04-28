package com.example.upcity.page;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upcity.adapters.AnimationUtilsHelper;
import com.example.upcity.network.ApplicationAllLoadHelper;
import com.example.upcity.adapters.ApplicationAdapter;
import com.example.upcity.R;
import com.example.upcity.adapters.ToolbarFragment;
import com.example.upcity.utils.Application;

import java.util.List;

public class AllApplicationPage extends AppCompatActivity {

    private ApplicationAllLoadHelper applicationAllLoadHelper;

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

        // Подключение зависимостей
        RecyclerView AllList = findViewById(R.id.AllList);
        Button HomeButton = findViewById(R.id.HomeButton);

        // Подключение кнопок
        HomeButton.setOnClickListener(view -> {
            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        });

        GridLayoutManager gridLayoutManagerAllList = new GridLayoutManager(this, 2);
        AllList.setLayoutManager(gridLayoutManagerAllList);

        // Отображение всех заявок
        applicationAllLoadHelper = new ApplicationAllLoadHelper();
        applicationAllLoadHelper.loadApplications(this, new ApplicationAllLoadHelper.ApplicationCallback() {
            @Override
            public void onSuccess(List<Application> applications) {
                ApplicationAdapter adapter = new ApplicationAdapter(applications);
                AllList.setAdapter(adapter);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(AllApplicationPage.this, "Ошибка в загрузке всех заявок: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

