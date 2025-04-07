package com.example.upcity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button RegistrationButton = findViewById(R.id.RegistrationButton);
        Button LoginButton = findViewById(R.id.LoginButton);

        RegistrationButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, 0);
            finish();
        });

        LoginButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, HomePage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_out, 0);
            finish();
        });
    }

}