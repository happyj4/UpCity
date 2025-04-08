package com.example.upcity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class BuyPremiumPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premium_page);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new ToolbarFragment())
                    .commit();
        }

        Button BuyPremiumButton = findViewById(R.id.BuyPremiumButton);
        Button HomeButton = findViewById(R.id.HomeButton);

        HomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(BuyPremiumPage.this, HomePage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, 0);
            finish();
        });

        BuyPremiumButton.setOnClickListener(view -> {
            Intent intent = new Intent(BuyPremiumPage.this, MessagePage.class);

            intent.putExtra("name", "Підписка активована!");
            intent.putExtra("description", "Преміум підписка успішно активована");

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, 0);
            finish();
        });
    }
}