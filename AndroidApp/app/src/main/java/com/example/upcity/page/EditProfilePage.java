package com.example.upcity.page;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.upcity.adapters.AdapterAnimation;
import com.example.upcity.R;
import com.example.upcity.adapters.FragmentToolbar;
import com.example.upcity.helpers.UpdateUserInfo;
import com.google.android.material.imageview.ShapeableImageView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class EditProfilePage extends AppCompatActivity {

    private File selectedPhoto;
    ShapeableImageView PhotoImage;

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
        ShapeableImageView EditPhotoButton = findViewById(R.id.EditPhotoButton);
        EditText EmailEditText = findViewById(R.id.EmailEditText);
        EditText NameEditText = findViewById(R.id.NameEditText);
        EditText SurnameEditText = findViewById(R.id.SurnameEditText);
        PhotoImage = findViewById(R.id.PhotoImage);

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

        EditPhotoButton.setOnClickListener(view -> {
            openGallery();
        });

        // Показ сообщения
        EditConfirmButton.setOnClickListener(view -> {
            String email = EmailEditText.getText().toString().trim();
            String name = NameEditText.getText().toString().trim();
            String surname = SurnameEditText.getText().toString().trim();
            SharedPreferences prefs = getSharedPreferences("USER_INFO", MODE_PRIVATE);

            UpdateUserInfo.updateProfile(EditProfilePage.this, name, surname, email, selectedPhoto, new UpdateUserInfo.UpdateCallback() {
                @Override
                public void onSuccess(com.example.upcity.utils.ResponseUpdateProfile.User user) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("email", user.getEmail());
                    editor.putString("name", user.getName());
                    editor.putString("surname", user.getSurname());
                    editor.putString("image", user.getImage());
                    editor.apply();

                    Intent intent = new Intent(EditProfilePage.this, MessagePage.class);
                    intent.putExtra("name", "Профіль змінено!");
                    intent.putExtra("description", "Дані вашого профілю успішно змінено");
                    AdapterAnimation.animateAndNavigate(EditProfilePage.this, R.id.linearLayout, R.anim.slide_out_left, MessagePage.class, intent);
                }

                @Override
                public void onError(String error) {
                    Intent intent = new Intent(EditProfilePage.this, MessagePage.class);
                    intent.putExtra("name", "Помилка!");
                    intent.putExtra("description", error);
                    AdapterAnimation.animateAndNavigate(EditProfilePage.this, R.id.linearLayout, R.anim.slide_out_left, MessagePage.class, intent);
                }
            });
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

                PhotoImage.setImageURI(imageUri);

            } catch (Exception e) {
                Log.e("EditProfile", "Ошибка при копировании изображения", e);
                Toast.makeText(this, "Ошибка при загрузке изображения", Toast.LENGTH_SHORT).show();
                selectedPhoto = null;
            }
        }
    }

    private File createTempImageFile(Uri imageUri) throws Exception {
        File tempFile = new File(getCacheDir(), "temp_profile_image_" + System.currentTimeMillis() + ".jpg");

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (selectedPhoto != null && selectedPhoto.exists()) {
            selectedPhoto.delete();
        }
    }

    //Изменяет кнопку назад
    @Override
    public void onBackPressed() {
        AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
    }
}
