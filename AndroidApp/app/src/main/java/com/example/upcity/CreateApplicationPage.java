package com.example.upcity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.imageview.ShapeableImageView;

public class CreateApplicationPage extends AppCompatActivity {
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_application_page);

        Button HomeButton = findViewById(R.id.HomeButton);
        ShapeableImageView PhotoButton = findViewById(R.id.PhotoButton);
        Button CreateApplicationButton = findViewById(R.id.CreateApplicationButton);

        menu = new Menu();
        PhotoButton.setOnClickListener(view -> menu.showPopupMenu(view, CreateApplicationPage.this));

        HomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(CreateApplicationPage.this, HomePageActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        CreateApplicationButton.setOnClickListener(view -> {
            Intent intent = new Intent(CreateApplicationPage.this, MessagePage.class);

            intent.putExtra("name", "Звернення Створено!");
            intent.putExtra("description", "Впродовж 24 годин воно буде опрацьоване");

            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
    }
}