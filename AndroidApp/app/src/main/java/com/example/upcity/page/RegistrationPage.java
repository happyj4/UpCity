package com.example.upcity.page;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.upcity.adapters.AnimationUtilsHelper;
import com.example.upcity.R;
import com.example.upcity.network.RegistrationHelper;
import com.example.upcity.utils.LoginRequest;
import com.example.upcity.utils.User;

public class RegistrationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);

        // Подключение зависимостей
        Button loginButton = findViewById(R.id.LoginButton);
        Button registrationButton = findViewById(R.id.RegistrationButton);
        EditText emailEditText  = findViewById(R.id.EmailEditText);
        EditText nameEditText  = findViewById(R.id.NameEditText);
        EditText surnameEditText  = findViewById(R.id.SurnameEditText);
        EditText passwordEditText  = findViewById(R.id.PasswordEditText);

        // Подключение кнопок
        loginButton.setOnClickListener(view -> {
            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, LoginPage.class, null);
        });

        registrationButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String name = nameEditText.getText().toString();
            String surname = surnameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            User user = new User(email, name, surname, password);

            RegistrationHelper registrationHelper = new RegistrationHelper();

            registrationHelper.registerUser(this, user, new RegistrationHelper.RegistrationCallback() {
                @Override
                public void onSuccess(String message) {
                    Intent intent = new Intent(RegistrationPage.this, HomePage.class);
                    intent.putExtra("skipAnimation", true);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_out, 0);
                    finish();
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(RegistrationPage.this, "Ошибка регистрации: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}