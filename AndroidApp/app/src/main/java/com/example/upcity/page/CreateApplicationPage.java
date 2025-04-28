package com.example.upcity.page;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.upcity.adapters.AnimationUtilsHelper;
import com.example.upcity.R;
import com.example.upcity.adapters.ToolbarFragment;

public class CreateApplicationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_application);
        AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new ToolbarFragment())
                    .commit();
        }

        // Подключение зависимостей
        Button CreateApplicationButton = findViewById(R.id.CreateApplicationButton);
        Button HomeButton = findViewById(R.id.HomeButton);

        // Подключение кнопок
        HomeButton.setOnClickListener(view -> {
            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        });

        // Показ сообщения
        CreateApplicationButton.setOnClickListener(view -> {
            Intent intent = new Intent(CreateApplicationPage.this, MessagePage.class);

            intent.putExtra("name", "Звернення Створено!");
            intent.putExtra("description", "Впродовж 24 годин воно буде опрацьоване");

            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_left, MessagePage.class, intent);
        });
    }
}