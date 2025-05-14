package com.example.upcity.page;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.upcity.R;
import com.example.upcity.adapters.AdapterAnimation;
import com.example.upcity.adapters.FragmentToolbar;
import com.example.upcity.helpers.CreateApplication;
import com.example.upcity.helpers.LoadUtilityCompany;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CreateApplicationPage extends AppCompatActivity {

    private String[] UtilityCompanies;
    private File selectedPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_application);
        AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new FragmentToolbar())
                    .commit();
        }

        // Подключение зависимостей
        Button CreateApplicationButton = findViewById(R.id.CreateApplicationButton);
        Button HomeButton = findViewById(R.id.HomeButton);
        ImageView AddPhotoButton = findViewById(R.id.AddPhotoButton);
        TextView NameEditText = findViewById(R.id.NameEditText);
        TextView AddressEditText = findViewById(R.id.AddressEditText);
        TextView DescriptionEditText = findViewById(R.id.DescriptionEditText);

        // Подключение спинера КП
        loadUtilityCompanies();

        // Подключение кнопок
        HomeButton.setOnClickListener(view -> {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        });

        AddPhotoButton.setOnClickListener(view -> {
            openGallery();
        });

        // Показ сообщения после создания заявки
        CreateApplicationButton.setOnClickListener(view -> {

            String name = NameEditText.getText().toString();
            String address = AddressEditText.getText().toString();
            String description = DescriptionEditText.getText().toString();
            String companyName = ((Spinner) findViewById(R.id.SpinnerUtilityCompany)).getSelectedItem().toString();

            if (selectedPhoto != null) {
                CreateApplication.createApplication(CreateApplicationPage.this, name, address, description, companyName, selectedPhoto, new CreateApplication.ApplicationCreationCallback() {
                    @Override
                    public void onApplicationCreated(String message) {
                        Intent intent = new Intent(CreateApplicationPage.this, MessagePage.class);
                        intent.putExtra("name", "Звернення Створено!");
                        intent.putExtra("description", "Впродовж 24 годин воно буде опрацьоване");

                        AdapterAnimation.animateAndNavigate(CreateApplicationPage.this, R.id.linearLayout, R.anim.slide_out_left, MessagePage.class, intent);
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Intent intent = new Intent(CreateApplicationPage.this, MessagePage.class);
                        intent.putExtra("name", "Помилка!");
                        intent.putExtra("description", errorMessage);

                        AdapterAnimation.animateAndNavigate(CreateApplicationPage.this, R.id.linearLayout, R.anim.slide_out_left, MessagePage.class, intent);
                    }
                });
            } else {
                Intent intent = new Intent(CreateApplicationPage.this, MessagePage.class);
                intent.putExtra("name", "Помилка!");
                intent.putExtra("description", "Не додано фото");

                AdapterAnimation.animateAndNavigate(CreateApplicationPage.this, R.id.linearLayout, R.anim.slide_out_left, MessagePage.class, intent);
            }
        });
    }

    private void loadUtilityCompanies() {
        LoadUtilityCompany helper = new LoadUtilityCompany();
        helper.loadUtilityCompany(this, new LoadUtilityCompany.ApplicationCallback() {
            @Override
            public void onSuccess(List<String> companyNames) {
                UtilityCompanies = companyNames.toArray(new String[0]);

                runOnUiThread(() -> {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateApplicationPage.this, R.layout.item_spinner_utility_company, UtilityCompanies);
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

            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(imageUri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                String filePath = cursor.getString(columnIndex);
                selectedPhoto = new File(filePath);
                cursor.close();
            }


            ImageView AddPhotoButton = findViewById(R.id.AddPhotoButton);
            AddPhotoButton.setImageURI(imageUri);
        }
    }
}
