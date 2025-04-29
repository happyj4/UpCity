package com.example.upcity.page;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.upcity.adapters.AnimationUtilsHelper;
import com.example.upcity.R;
import com.example.upcity.network.GoogleAuthHelper;
import com.example.upcity.network.LoginHelper;
import com.example.upcity.utils.LoginRequest;

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

        // Подключение зависимостей
        Button registrationButton = findViewById(R.id.RegistrationButton);
        Button loginButton = findViewById(R.id.LoginButton);
        ImageButton googleButton = findViewById(R.id.GoogleButton);
        EditText emailEditText  = findViewById(R.id.EmailEditText);
        EditText passwordEditText  = findViewById(R.id.PasswordEditText);

        // Авторизация через гугл
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

        // Подключение кнопок
        googleButton.setOnClickListener(view -> googleAuthHelper.signIn());

        registrationButton.setOnClickListener(view -> {
            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_left, RegistrationPage.class, null);
        });

        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            LoginRequest loginRequest = new LoginRequest(email, password);
            performLogin(loginRequest);
        });
    }

    private void performLogin(LoginRequest loginRequest) {
        LoginHelper loginHelper = new LoginHelper();

        loginHelper.login(this, loginRequest, new LoginHelper.LoginCallback() {
            @Override
            public void onSuccess(String response) {
                Intent intent = new Intent(LoginPage.this, HomePage.class);
                intent.putExtra("skipAnimation", true);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_out, 0);
                finish();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(LoginPage.this, "Ошибка входа: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleAuthHelper.handleActivityResult(requestCode, resultCode, data);
    }
}
