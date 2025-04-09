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
        AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new ToolbarFragment())
                    .commit();
        }

        Button CreateApplicationButton = findViewById(R.id.CreateApplicationButton);
        Button HomeButton = findViewById(R.id.HomeButton);

        HomeButton.setOnClickListener(view -> {
            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        });

        CreateApplicationButton.setOnClickListener(view -> {
            Intent intent = new Intent(CreateApplicationPage.this, MessagePage.class);

            intent.putExtra("name", "Звернення Створено!");
            intent.putExtra("description", "Впродовж 24 годин воно буде опрацьоване");

            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_left, MessagePage.class, intent);
        });
    }
}