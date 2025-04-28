package com.example.upcity.page;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.upcity.adapters.AnimationUtilsHelper;
import com.example.upcity.R;
import com.example.upcity.network.GoogleAuthHelper;

public class LoginPage extends AppCompatActivity {

    private GoogleAuthHelper googleAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        boolean skipAnimation = getIntent().getBooleanExtra("skipAnimation", false);

        if (!skipAnimation) {
            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_left, null, null);
        }

        Button registrationButton = findViewById(R.id.RegistrationButton);
        Button loginButton = findViewById(R.id.LoginButton);
        ImageButton googleButton = findViewById(R.id.GoogleButton);

        googleAuthHelper = new GoogleAuthHelper(this);
        googleAuthHelper.setOnGoogleAuthListener(new GoogleAuthHelper.OnGoogleAuthListener() {
            @Override
            public void onSuccess(String name, String email) {
                Toast.makeText(LoginPage.this, "Вибраний акаунт:\nІм'я: " + name + "\nПошта: " + email, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(LoginPage.this, "Помилка входу: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        googleButton.setOnClickListener(view -> googleAuthHelper.signIn());

        registrationButton.setOnClickListener(view -> {
            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_left, RegistrationPage.class, null);
        });

        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginPage.this, HomePage.class);
            intent.putExtra("skipAnimation", true);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_out, 0);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleAuthHelper.handleActivityResult(requestCode, resultCode, data);
    }
}
