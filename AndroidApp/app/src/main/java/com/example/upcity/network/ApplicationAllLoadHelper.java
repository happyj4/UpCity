package com.example.upcity.network;

import android.content.Context;
import android.widget.Toast;

import com.example.upcity.utils.Application;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationAllLoadHelper {
    public void loadApplications(Context context, final ApplicationCallback callback) {
        ApiService apiService = RetrofitClient.getInstance();
        Call<List<Application>> call = apiService.getApplications();

        call.enqueue(new Callback<List<Application>>() {
            @Override
            public void onResponse(Call<List<Application>> call, Response<List<Application>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    Toast.makeText(context, "Ошибка загрузки заявок", Toast.LENGTH_SHORT).show();
                    callback.onFailure("Ошибка загрузки заявок");
                }
            }

            @Override
            public void onFailure(Call<List<Application>> call, Throwable t) {
                Toast.makeText(context, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_LONG).show();
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface ApplicationCallback {
        void onSuccess(List<Application> applications);
        void onFailure(String error);
    }
}
