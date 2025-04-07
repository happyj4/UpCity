package com.example.upcity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.imageview.ShapeableImageView;

public class CreateApplicationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_application_page);
        addToolbar();

        Button CreateApplicationButton = findViewById(R.id.CreateApplicationButton);

        CreateApplicationButton.setOnClickListener(view -> {
            Intent intent = new Intent(CreateApplicationPage.this, MessagePage.class);

            intent.putExtra("name", "Звернення Створено!");
            intent.putExtra("description", "Впродовж 24 годин воно буде опрацьоване");

            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
    }

    public void addToolbar() {
        Menu menu;

        ImageButton PlusButton = findViewById(R.id.PlusButton);
        ShapeableImageView PhotoButton = findViewById(R.id.PhotoButton);
        Button HomeButton = findViewById(R.id.HomeButton);

        menu = new Menu();
        PhotoButton.setOnClickListener(view -> menu.showPopupMenu(view, CreateApplicationPage.this));

        PlusButton.setOnClickListener(view -> {
            Intent intent = new Intent(CreateApplicationPage.this, CreateApplicationPage.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        HomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(CreateApplicationPage.this, HomePage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, 0);
            finish();
        });
    }
}