package com.example.upcity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;

public class MessagePage extends AppCompatActivity {
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_page);

        Button HomeButton = findViewById(R.id.HomeButton);
        ShapeableImageView PhotoButton = findViewById(R.id.PhotoButton);
        TextView NameMessageText = findViewById(R.id.NameMessageText);
        TextView DescriptionMessageText = findViewById(R.id.DescriptionMessageText);

        Intent oldintent = getIntent();
        NameMessageText.setText(oldintent.getStringExtra("name"));
        DescriptionMessageText.setText(oldintent.getStringExtra("description"));

        menu = new Menu();
        PhotoButton.setOnClickListener(view -> menu.showPopupMenu(view, MessagePage.this));

        HomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(MessagePage.this, HomePageActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
    }
}