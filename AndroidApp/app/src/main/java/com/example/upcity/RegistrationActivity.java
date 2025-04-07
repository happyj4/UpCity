package com.example.upcity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        Button LoginButton = findViewById(R.id.LoginButton);

        LoginButton.setOnClickListener(view -> {
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, 0);
            finish();
        });
    }
}