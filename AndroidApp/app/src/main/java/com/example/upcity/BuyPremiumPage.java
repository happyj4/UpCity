package com.example.upcity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;

public class BuyPremiumPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premium_page);
        addToolbar();

        Button BuyPremiumButton = findViewById(R.id.BuyPremiumButton);

        BuyPremiumButton.setOnClickListener(view -> {
            Intent intent = new Intent(BuyPremiumPage.this, MessagePage.class);

            intent.putExtra("name", "Підписка активована!");
            intent.putExtra("description", "Преміум підписка успішно активована");

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
        PhotoButton.setOnClickListener(view -> menu.showPopupMenu(view, BuyPremiumPage.this));

        PlusButton.setOnClickListener(view -> {
            Intent intent = new Intent(BuyPremiumPage.this, BuyPremiumPage.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        HomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(BuyPremiumPage.this, HomePage.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
    }
}