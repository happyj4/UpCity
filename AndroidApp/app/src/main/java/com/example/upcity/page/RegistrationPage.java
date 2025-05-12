package com.example.upcity.page;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.upcity.adapters.AnimationUtilsHelper;
import com.example.upcity.R;
import com.example.upcity.helpers.RegistrationHelper;
import com.example.upcity.utils.UserRequest;

public class RegistrationPage extends AppCompatActivity {
    TextView ErrorMessage;

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
        CheckBox checkBox = findViewById(R.id.checkbox);
        ErrorMessage = findViewById(R.id.ErrorMessage);

        // Подключение кнопок
        loginButton.setOnClickListener(view -> {
            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, LoginPage.class, null);
        });

        registrationButton.setOnClickListener(view -> {
            if (checkBox.isChecked()) {
            checkBox.setTextColor(Color.parseColor("#848484"));
            checkBox.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#848484")));

            String email = emailEditText.getText().toString();
            String name = nameEditText.getText().toString();
            String surname = surnameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            UserRequest userRequest = new UserRequest(email, name, surname, password);
            performRegistration(userRequest);

            } else {
                checkBox.setTextColor(Color.parseColor("#D54343"));
                checkBox.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#D54343")));
                ErrorMessage.setText("Погодьтеся з умовами користування та політикою конфіденційності");
            }
        });
    }

    private void performRegistration(UserRequest userRequest) {
        RegistrationHelper registrationHelper = new RegistrationHelper();

        registrationHelper.registerUser(this, userRequest, new RegistrationHelper.RegistrationCallback() {
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
                if (error.equals("value is not a valid email address: An email address must have an @-sign.")) {
                    ErrorMessage.setText("Введіть коректний Email");
                } else if (error.equals("String should have at least 8 characters")) {
                    ErrorMessage.setText("Пароль має містити щонайменше 8 символів");
                } else if (error.equals("String should have at least 3 characters")) {
                    ErrorMessage.setText("Поля повинні містити щонайменше 3 символи");
                } else {
                    ErrorMessage.setText(error);
                }
            }
        });
    }
}