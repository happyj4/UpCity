package com.example.upcity.page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.upcity.adapters.AdapterAnimation;
import com.example.upcity.adapters.FragmentMenu;
import com.example.upcity.R;
import com.example.upcity.adapters.FragmentToolbar;

public class MessagePage extends AppCompatActivity {

    private String inputName;
    private String inputAddress;
    private String inputDescription;
    private String inputCompany;
    private String inputPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new FragmentToolbar())
                    .commit();
        }

        // Подключение зависимостей
        TextView NameMessageText = findViewById(R.id.NameMessageText);
        TextView DescriptionMessageText = findViewById(R.id.DescriptionMessageText);
        Button HomeButton = findViewById(R.id.HomeButton);

        // Подключение кнопок
        HomeButton.setOnClickListener(view -> {
            if (!hasDraftData(inputName, inputAddress, inputDescription, inputCompany, inputPhotoPath)) {
                AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
            } else {
                Intent intent = new Intent(this, CreateApplicationPage.class);
                intent.putExtra("input_name", inputName);
                intent.putExtra("input_address", inputAddress);
                intent.putExtra("input_description", inputDescription);
                intent.putExtra("input_company", inputCompany);
                intent.putExtra("input_photo_path", inputPhotoPath);
                AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, CreateApplicationPage.class, intent);
            }
        });

        // Показ сообщений
        Intent oldintent = getIntent();
        NameMessageText.setText(oldintent.getStringExtra("name"));
        DescriptionMessageText.setText(oldintent.getStringExtra("description"));

        inputName = oldintent.getStringExtra("input_name");
        inputAddress = oldintent.getStringExtra("input_address");
        inputDescription = oldintent.getStringExtra("input_description");
        inputCompany = oldintent.getStringExtra("input_company");
        inputPhotoPath = oldintent.getStringExtra("input_photo_path");

        if (hasDraftData(inputName, inputAddress, inputDescription, inputCompany, inputPhotoPath)) {
            HomeButton.setText("Редагувати заяву");
        }
    }

    private boolean hasDraftData(String name, String address, String description, String company, String photoPath) {
        return !(isEmpty(name) && isEmpty(address) && isEmpty(description) && isEmpty(company) && isEmpty(photoPath));
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    //Изменяет кнопку назад
    @Override
    public void onBackPressed() {
        if (!hasDraftData(inputName, inputAddress, inputDescription, inputCompany, inputPhotoPath)) {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        } else {
            Intent intent = new Intent(this, CreateApplicationPage.class);
            intent.putExtra("input_name", inputName);
            intent.putExtra("input_address", inputAddress);
            intent.putExtra("input_description", inputDescription);
            intent.putExtra("input_company", inputCompany);
            intent.putExtra("input_photo_path", inputPhotoPath);
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, CreateApplicationPage.class, intent);
        }

    }
}