package com.example.upcity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class MyApplicationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_application_page);
        addToolbar();

        RecyclerView AllList = findViewById(R.id.AllList);

        GridLayoutManager gridLayoutManagerAllList = new GridLayoutManager(this, 2);
        AllList.setLayoutManager(gridLayoutManagerAllList);

        List<Application> applicationList = new ArrayList<>();
        applicationList.add(new Application(1, "Заява 1", "01.01.2022", 123));
        applicationList.add(new Application(2, "Заява 2", "02.02.2022", 124));
        applicationList.add(new Application(3, "Заява 3", "03.03.2022", 125));

        applicationList.add(0, null);

        ApplicationAdapter adapter = new ApplicationAdapter(applicationList, new ApplicationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Application application) {
                if (application == null) {
                    Intent intent = new Intent(MyApplicationPage.this, CreateApplicationPage.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else {
                    Toast.makeText(MyApplicationPage.this, "Клик на: " + application.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        AllList.setAdapter(adapter);
    }

    public void addToolbar() {
        Menu menu;

        ImageButton PlusButton = findViewById(R.id.PlusButton);
        ShapeableImageView PhotoButton = findViewById(R.id.PhotoButton);
        Button HomeButton = findViewById(R.id.HomeButton);

        menu = new Menu();
        PhotoButton.setOnClickListener(view -> menu.showPopupMenu(view, MyApplicationPage.this));

        PlusButton.setOnClickListener(view -> {
            Intent intent = new Intent(MyApplicationPage.this, CreateApplicationPage.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        HomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(MyApplicationPage.this, HomePage.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
    }
}