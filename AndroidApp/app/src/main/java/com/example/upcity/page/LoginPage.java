package com.example.upcity.page;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.upcity.adapters.AdapterAnimation;
import com.example.upcity.R;
import com.example.upcity.helpers.GoogleAuthentication;
import com.example.upcity.helpers.HelperLogin;
import com.example.upcity.utils.RequestLogin;

public class LoginPage extends AppCompatActivity {

    private GoogleAuthentication googleAuthentication;
    TextView ErrorMessage;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        boolean skipAnimation = getIntent().getBooleanExtra("skipAnimation", false);

        if (!skipAnimation) {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_left, null, null);
        }

        // Подключение зависимостей
        Button registrationButton = findViewById(R.id.RegistrationButton);
        Button loginButton = findViewById(R.id.LoginButton);
        ImageButton googleButton = findViewById(R.id.GoogleButton);
        EditText emailEditText  = findViewById(R.id.EmailEditText);
        EditText passwordEditText  = findViewById(R.id.PasswordEditText);
        ErrorMessage = findViewById(R.id.ErrorMessage);

        // Авторизация через гугл
        googleAuthentication = new GoogleAuthentication(this);
        googleAuthentication.setOnGoogleAuthListener(new GoogleAuthentication.OnGoogleAuthListener() {
            @Override
            public void onSuccess(String success) {
                Intent intent = new Intent(LoginPage.this, HomePage.class);
                intent.putExtra("skipAnimation", true);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_out, 0);
                finish();
            }

            @Override
            public void onFailure(String error) {
                ErrorMessage.setText("Помилка входу");
            }
        });

        // Подключение кнопок
        googleButton.setOnClickListener(view -> googleAuthentication.signIn());

        registrationButton.setOnClickListener(view -> {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_left, RegistrationPage.class, null);
        });

        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            RequestLogin requestLogin = new RequestLogin(email, password);
            performLogin(requestLogin);
        });
    }

    private void performLogin(RequestLogin requestLogin) {
        HelperLogin helperLogin = new HelperLogin();

        helperLogin.login(this, requestLogin, new HelperLogin.LoginCallback() {
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
                if (error.equals("Помилка")) {
                    ErrorMessage.setText("Невірна пошта користувача");
                } else {
                    ErrorMessage.setText(error);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleAuthentication.handleActivityResult(requestCode, resultCode, data);
    }

    //Изменяет кнопку назад
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            if (backToast != null) backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Якщо хочеш вийти, натисни ще раз", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
