package com.example.upcity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CreateApplicationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_application_page);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new ToolbarFragment())
                    .commit();
        }

        Button CreateApplicationButton = findViewById(R.id.CreateApplicationButton);
        Button HomeButton = findViewById(R.id.HomeButton);

        HomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(CreateApplicationPage.this, HomePage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, 0);
            finish();
        });

        CreateApplicationButton.setOnClickListener(view -> {
            Intent intent = new Intent(CreateApplicationPage.this, MessagePage.class);

            intent.putExtra("name", "Звернення Створено!");
            intent.putExtra("description", "Впродовж 24 годин воно буде опрацьоване");

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, 0);
            finish();
        });
    }
}