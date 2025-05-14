package com.example.upcity.page;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.upcity.adapters.AdapterAnimation;
import com.example.upcity.R;
import com.example.upcity.adapters.FragmentToolbar;

public class EditProfilePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new FragmentToolbar())
                    .commit();
        }

        // Подключение зависимостей
        Button EditConfirmButton = findViewById(R.id.EditConfirmButton);
        Button HomeButton = findViewById(R.id.HomeButton);
        EditText EmailEditText = findViewById(R.id.EmailEditText);
        EditText NameEditText = findViewById(R.id.NameEditText);
        EditText SurnameEditText = findViewById(R.id.SurnameEditText);
        ImageView PhotoImage = findViewById(R.id.PhotoImage);

        //Отображение информации пользователя
        EmailEditText.setText(getUserEmail(this));
        NameEditText.setText(getUserName(this));
        SurnameEditText.setText(getUserSurname(this));

        String imageUrl = getImage(this);
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(EditProfilePage.this)
                    .load(imageUrl)
                    .into(PhotoImage);
        }

        // Подключение кнопок
        HomeButton.setOnClickListener(view -> {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        });

        // Показ сообщения
        EditConfirmButton.setOnClickListener(view -> {
            Intent intent = new Intent(EditProfilePage.this, MessagePage.class);

            intent.putExtra("name", "Профіль змінено!");
            intent.putExtra("description", "Дані вашого профілю успішно змінено");

            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_left, MessagePage.class, intent);
        });
    }

    public static String getUserEmail(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        return prefs.getString("email", null);
    }

    public static String getUserName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        return prefs.getString("name", null);
    }

    public static String getUserSurname(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        return prefs.getString("surname", null);
    }

    public static String getImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);

        return prefs.getString("image", null);
    }
}