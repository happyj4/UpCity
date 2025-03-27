package com.example.upcity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        ImageButton PlusButton = findViewById(R.id.PlusButton);
        RecyclerView MyList = findViewById(R.id.MyList);
        RecyclerView AllList = findViewById(R.id.AllList);
        ShapeableImageView PhotoButton = findViewById(R.id.PhotoButton);

        menu = new Menu();
        PhotoButton.setOnClickListener(view -> menu.showPopupMenu(view, HomePageActivity.this));

        PlusButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, CreateApplicationPage.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        LinearLayoutManager layoutManagerMyList = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        MyList.setLayoutManager(layoutManagerMyList);

        GridLayoutManager gridLayoutManagerAllList = new GridLayoutManager(this, 2);
        AllList.setLayoutManager(gridLayoutManagerAllList);

        List<Application> applicationList = new ArrayList<>();
        applicationList.add(new Application(1, "Заява 1", "01.01.2022", 123));
        applicationList.add(new Application(2, "Заява 2", "02.02.2022", 124));
        applicationList.add(new Application(3, "Заява 3", "03.03.2022", 125));

        ApplicationAdapter adapter = new ApplicationAdapter(applicationList, new ApplicationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Application application) {
                Toast.makeText(HomePageActivity.this, "Клик на: " + application.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        MyList.setAdapter(adapter);
        AllList.setAdapter(adapter);

    }
}