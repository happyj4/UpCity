package com.example.upcity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfilePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new ToolbarFragment())
                    .commit();
        }

        Button EditConfirmButton = findViewById(R.id.EditConfirmButton);
        Button HomeButton = findViewById(R.id.HomeButton);

        HomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(EditProfilePage.this, HomePage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, 0);
            finish();
        });

        EditConfirmButton.setOnClickListener(view -> {
            Intent intent = new Intent(EditProfilePage.this, MessagePage.class);

            intent.putExtra("name", "Профіль змінено!");
            intent.putExtra("description", "Дані вашого профілю успішно змінено");

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, 0);
            finish();
        });
    }
}