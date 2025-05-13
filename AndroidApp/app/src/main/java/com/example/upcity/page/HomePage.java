package com.example.upcity.page;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upcity.adapters.AnimationUtilsHelper;
import com.example.upcity.helpers.ApplicationAllLoadHelper;
import com.example.upcity.helpers.UserApplicationsLoadHelper;
import com.example.upcity.utils.ApplicationRequest;
import com.example.upcity.adapters.ApplicationAdapter;
import com.example.upcity.R;
import com.example.upcity.adapters.ToolbarFragment;

import java.util.List;

public class HomePage extends AppCompatActivity {

    private ApplicationAllLoadHelper applicationAllLoadHelper;
    private UserApplicationsLoadHelper userApplicationsLoadHelper;

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

        // Подключение зависимостей
        RecyclerView MyList = findViewById(R.id.MyList);
        RecyclerView AllList = findViewById(R.id.AllList);
        Button OpenAllButton = findViewById(R.id.OpenAllButton);
        Button OpenMyButton = findViewById(R.id.OpenMyButton);
        ImageButton MapButton = findViewById(R.id.MapButton);

        // Подключение кнопок
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

        // Отображение моих заявок
        userApplicationsLoadHelper = new UserApplicationsLoadHelper();
        UserApplicationsLoadHelper userApplicationsLoadHelper = new UserApplicationsLoadHelper();

        userApplicationsLoadHelper.getUserApplications(this, new UserApplicationsLoadHelper.ApplicationCallback() {
            @Override
            public void onSuccess(List<ApplicationRequest> applications) {
                ApplicationAdapter adapter = new ApplicationAdapter(applications);
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
        applicationAllLoadHelper = new ApplicationAllLoadHelper();
        applicationAllLoadHelper.loadApplications(this, new ApplicationAllLoadHelper.ApplicationCallback() {
            @Override
            public void onSuccess(List<ApplicationRequest> applications) {
                ApplicationAdapter adapter = new ApplicationAdapter(applications);
                AllList.setAdapter(adapter);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(HomePage.this, "Ошибка в загрузке всех заявок: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}