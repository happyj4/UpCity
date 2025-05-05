package com.example.upcity.page;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.upcity.adapters.AnimationUtilsHelper;
import com.example.upcity.R;
import com.example.upcity.adapters.ToolbarFragment;
import com.example.upcity.helpers.UtilityCompanyAllLoadHelper;

import java.util.List;

public class CreateApplicationPage extends AppCompatActivity {

    private String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_application);
        AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new ToolbarFragment())
                    .commit();
        }

        // Подключение зависимостей
        Button CreateApplicationButton = findViewById(R.id.CreateApplicationButton);
        Button HomeButton = findViewById(R.id.HomeButton);
        ImageView AddPhotoButton = findViewById(R.id.AddPhotoButton);

        // Подключение спинера КП
        loadUtilityCompanies();

        // Подключение кнопок
        HomeButton.setOnClickListener(view -> {
            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        });

        AddPhotoButton.setOnClickListener(view -> {
            openGallery();
        });

        // Показ сообщения
        CreateApplicationButton.setOnClickListener(view -> {
            Intent intent = new Intent(CreateApplicationPage.this, MessagePage.class);

            intent.putExtra("name", "Звернення Створено!");
            intent.putExtra("description", "Впродовж 24 годин воно буде опрацьоване");

            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_left, MessagePage.class, intent);
        });
    }

    private void loadUtilityCompanies() {
        UtilityCompanyAllLoadHelper helper = new UtilityCompanyAllLoadHelper();
        helper.loadUtilityCompany(this, new UtilityCompanyAllLoadHelper.ApplicationCallback() {
            @Override
            public void onSuccess(List<String> companyNames) {
                items = companyNames.toArray(new String[0]);

                runOnUiThread(() -> {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateApplicationPage.this, R.layout.item_spinner_utility_company, items);
                    adapter.setDropDownViewResource(R.layout.item_spinner_utility_company);
                    Spinner spinner = findViewById(R.id.SpinnerUtilityCompany);
                    spinner.setAdapter(adapter);
                });
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(CreateApplicationPage.this, "Ошибка загрузки КП: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            ImageView AddPhotoButton = findViewById(R.id.AddPhotoButton);
            AddPhotoButton.setImageURI(imageUri);

            String imagePath = imageUri.toString();
            Toast.makeText(this, "Фото вибрано", Toast.LENGTH_SHORT).show();
        }
    }

}