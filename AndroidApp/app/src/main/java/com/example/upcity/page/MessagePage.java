package com.example.upcity.page;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.upcity.adapters.AnimationUtilsHelper;
import com.example.upcity.adapters.Menu;
import com.example.upcity.R;
import com.example.upcity.adapters.ToolbarFragment;

public class MessagePage extends AppCompatActivity {
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new ToolbarFragment())
                    .commit();
        }

        // Подключение зависимостей
        TextView NameMessageText = findViewById(R.id.NameMessageText);
        TextView DescriptionMessageText = findViewById(R.id.DescriptionMessageText);
        Button HomeButton = findViewById(R.id.HomeButton);


        // Подключение кнопок
        HomeButton.setOnClickListener(view -> {
            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        });

        // Показ сообщений
        Intent oldintent = getIntent();
        NameMessageText.setText(oldintent.getStringExtra("name"));
        DescriptionMessageText.setText(oldintent.getStringExtra("description"));
    }
}