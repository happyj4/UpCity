package com.example.upcity.page;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.upcity.R;
import com.example.upcity.network.ApiService;
import com.example.upcity.network.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class DeleteApplicationPage extends AppCompatActivity {

    private Button CancleButton;
    private Button DeleteButton;
    private int applicationId;
    private String activity = "HomePage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_application);

        // Принимаю данные прошлой активности
        Intent intent = getIntent();
        applicationId = intent.getIntExtra("applicationId", -1);
        activity = intent.getStringExtra("activity");

        // Подключение зависимостей
        CancleButton = findViewById(R.id.CancleButton);
        DeleteButton = findViewById(R.id.DeleteButton);

        // Подключение кнопок
        CancleButton.setOnClickListener(view -> {
            Intent Intent = new Intent(this, ViewApplicationPage.class);
            Intent.putExtra("skipAnimation", true);
            Intent.putExtra("applicationId", applicationId);
            Intent.putExtra("activity", activity);
            startActivity(Intent);
            overridePendingTransition(R.anim.slide_in_out, 0);
            finish();
        });

        DeleteButton.setOnClickListener(view -> {
            ApiService apiService = RetrofitClient.getInstance();
            SharedPreferences prefs = this.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
            String accessToken = "Bearer " + prefs.getString("access_token", null);

            Call<ResponseBody> call = apiService.deleteApplication(accessToken, applicationId);

            call.enqueue(new retrofit2.Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String className = "com.example.upcity.page." + activity;
                            Class<?> clazz = Class.forName(className);
                            Intent Intent = new Intent(view.getContext(), clazz);
                            Intent.putExtra("skipAnimation", true);
                            Intent.putExtra("applicationId", applicationId);
                            startActivity(Intent);
                            overridePendingTransition(R.anim.slide_in_out, 0);
                            finish();
                        } catch (ClassNotFoundException e) {
                            Intent Intent = new Intent(view.getContext(), HomePage.class);
                            Intent.putExtra("skipAnimation", true);
                            Intent.putExtra("applicationId", applicationId);
                            startActivity(Intent);
                            overridePendingTransition(R.anim.slide_in_out, 0);
                            finish();
                        }
                    } else {
                        Log.e("DELETE", "Помилка: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("DELETE", "Помилка: " + t.getMessage());
                }
            });
        });
    }

    //Изменяет кнопку назад
    @Override
    public void onBackPressed() {
        Intent Intent = new Intent(this, ViewApplicationPage.class);
        Intent.putExtra("skipAnimation", true);
        Intent.putExtra("applicationId", applicationId);
        Intent.putExtra("activity", activity);
        startActivity(Intent);
        overridePendingTransition(R.anim.slide_in_out, 0);
        finish();
    }
}

