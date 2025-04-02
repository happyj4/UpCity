package com.example.upcity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;

public class EditProfilePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        addToolbar();

        Button EditConfirmButton = findViewById(R.id.EditConfirmButton);

        EditConfirmButton.setOnClickListener(view -> {
            Intent intent = new Intent(EditProfilePage.this, MessagePage.class);

            intent.putExtra("name", "Профіль змінено!");
            intent.putExtra("description", "Дані вашого профілю успішно змінено");

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
        PhotoButton.setOnClickListener(view -> menu.showPopupMenu(view, EditProfilePage.this));

        PlusButton.setOnClickListener(view -> {
            Intent intent = new Intent(EditProfilePage.this, EditProfilePage.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        HomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(EditProfilePage.this, HomePage.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
    }
}