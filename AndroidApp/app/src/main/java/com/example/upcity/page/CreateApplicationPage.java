package com.example.upcity.page;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateApplicationPage extends AppCompatActivity {

    private static final String DEFAULT_CITY = "Харків, Україна";
    private boolean deletePhoto = true;
    private String[] UtilityCompanies;
    private File selectedPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_application);

        Intent OldIntent = getIntent();
        if (OldIntent != null && (OldIntent.hasExtra("input_name") || OldIntent.hasExtra("input_address") ||
                OldIntent.hasExtra("input_description") || OldIntent.hasExtra("input_company") ||
                OldIntent.hasExtra("input_photo_path"))) {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_left, null, null);

            String name = OldIntent.getStringExtra("input_name");
            String address = OldIntent.getStringExtra("input_address");
            String description = OldIntent.getStringExtra("input_description");
            String company = OldIntent.getStringExtra("input_company");
            String photoPath = OldIntent.getStringExtra("input_photo_path");

            restoreInputData(name, address, description, company, photoPath);
        } else {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);
        }

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
            String address = convertAddress(AddressEditText.getText().toString());
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
                        deletePhoto = false;

                        Intent intent = new Intent(CreateApplicationPage.this, MessagePage.class);
                        intent.putExtra("name", "Помилка!");
                        intent.putExtra("description", errorMessage);

                        intent.putExtra("input_name", name);
                        intent.putExtra("input_address", AddressEditText.getText().toString());
                        intent.putExtra("input_description", description);
                        intent.putExtra("input_company", companyName);
                        intent.putExtra("input_photo_path", selectedPhoto.getAbsolutePath());

                        AdapterAnimation.animateAndNavigate(CreateApplicationPage.this, R.id.linearLayout, R.anim.slide_out_left, MessagePage.class, intent);
                    }
                });
            } else {
                Intent intent = new Intent(CreateApplicationPage.this, MessagePage.class);
                intent.putExtra("name", "Помилка!");
                intent.putExtra("description", "Не додано фото");

                intent.putExtra("input_name", name);
                intent.putExtra("input_address", AddressEditText.getText().toString());
                intent.putExtra("input_description", description);
                intent.putExtra("input_company", companyName);
                intent.putExtra("input_photo_path", selectedPhoto);

                AdapterAnimation.animateAndNavigate(CreateApplicationPage.this, R.id.linearLayout, R.anim.slide_out_left, MessagePage.class, intent);
            }
        });
    }

    private void restoreInputData(String name, String address, String description, String company, String photoPath) {
        TextView NameEditText = findViewById(R.id.NameEditText);
        TextView AddressEditText = findViewById(R.id.AddressEditText);
        TextView DescriptionEditText = findViewById(R.id.DescriptionEditText);
        Spinner spinner = findViewById(R.id.SpinnerUtilityCompany);
        ImageView AddPhotoButton = findViewById(R.id.AddPhotoButton);

        if (name != null) {
            NameEditText.setText(name);
        }

        if (address != null) {
            AddressEditText.setText(address);
        }

        if (description != null) {
            DescriptionEditText.setText(description);
        }

        if (photoPath != null) {
            File photoFile = new File(photoPath);
            if (photoFile.exists()) {
                selectedPhoto = photoFile;
                AddPhotoButton.setImageURI(Uri.fromFile(photoFile));
            }
        }

        spinner.post(() -> {
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
            if (adapter != null && company != null) {
                int position = adapter.getPosition(company);
                if (position >= 0) {
                    spinner.setSelection(position);
                }
            }
        });

        if (photoPath != null) {
            File imgFile = new File(photoPath);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ImageView myImage = findViewById(R.id.AddPhotoButton);
                myImage.setImageBitmap(myBitmap);
            }
        }
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

            try {
                selectedPhoto = createTempImageFile(imageUri);

                ImageView AddPhotoButton = findViewById(R.id.AddPhotoButton);
                AddPhotoButton.setImageURI(imageUri);

            } catch (Exception e) {
                Log.e("CreateApplication", "Ошибка при копировании изображения", e);
                Toast.makeText(this, "Ошибка при загрузке изображения", Toast.LENGTH_SHORT).show();
                selectedPhoto = null;
            }
        }
    }

    private File createTempImageFile(Uri imageUri) throws Exception {
        File tempFile = new File(getCacheDir(), "temp_image_" + System.currentTimeMillis() + ".jpg");

        try (InputStream inputStream = getContentResolver().openInputStream(imageUri);
             FileOutputStream outputStream = new FileOutputStream(tempFile)) {

            if (inputStream == null) {
                throw new Exception("Не удалось открыть изображение");
            }

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }

    public static String convertAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return "";
        }

        address = address.trim();

        boolean containsCity = address.toLowerCase().contains("харків");

        Pattern pattern = Pattern.compile("(?:((?:[вВ][уУ][лЛ]\\.?|[вВ][уУ][лЛ][иИіІ][цЦ][яЯ])|(?:[пП][рР][оО][сС][пП][еЕ][кК][тТ])|(?:[бБ][уУ][лЛ][ьЬ][вВ][аА][рР]))\\s+)?([\\p{L}\\s]+[\\p{L}])\\s*[,\\s]?\\s*(\\d+(?:[\\-\\/]?\\p{L}*\\d*)?)");
        Matcher matcher = pattern.matcher(address);

        if (matcher.find()) {
            String streetType = matcher.group(1);
            String streetName = matcher.group(2).trim();
            String streetNumber = matcher.group(3);

            if (streetType == null || streetType.isEmpty() ||
                    streetType.toLowerCase().startsWith("вул")) {
                streetType = "вулиця";
            }

            StringBuilder result = new StringBuilder();
            result.append(streetType).append(" ").append(streetName).append(", ").append(streetNumber);

            if (!containsCity) {
                result.append(", ").append(DEFAULT_CITY);
            } else {
                int endOfNumberIndex = address.indexOf(streetNumber) + streetNumber.length();
                if (endOfNumberIndex < address.length()) {
                    String cityPart = address.substring(endOfNumberIndex).trim();
                    if (cityPart.startsWith(",")) {
                        cityPart = cityPart.substring(1).trim();
                    }

                    if (cityPart.toLowerCase().contains("харків")) {
                        result.append(", ").append(cityPart);
                    } else {
                        result.append(", ").append(DEFAULT_CITY);
                    }
                } else {
                    result.append(", ").append(DEFAULT_CITY);
                }
            }

            return result.toString();
        }

        return address + (!containsCity ? ", " + DEFAULT_CITY : "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (selectedPhoto != null && selectedPhoto.exists() && deletePhoto == true) {
            selectedPhoto.delete();
        }
    }

    //Изменяет кнопку назад
    @Override
    public void onBackPressed() {
        AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
    }
}
