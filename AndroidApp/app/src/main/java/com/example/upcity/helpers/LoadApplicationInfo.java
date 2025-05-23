package com.example.upcity.helpers;

import android.content.Context;
import com.example.upcity.network.ApiService;
import com.example.upcity.network.RetrofitClient;
import com.example.upcity.utils.ResponseApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadApplicationInfo {
    public static void loadApplicationDetails(Context context, int appId, String accessToken, ApplicationDetailsCallback callback) {
        ApiService apiService = RetrofitClient.getInstance();
        Call<ResponseApplication> call = apiService.getApplicationDetails("Bearer " + accessToken, appId);

        call.enqueue(new Callback<ResponseApplication>() {
            @Override
            public void onResponse(Call<ResponseApplication> call, Response<ResponseApplication> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onApplicationDetailsLoaded(response.body());
                } else {
                    callback.onError("Ошибка загрузки: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseApplication> call, Throwable t) {
                callback.onError("Ошибка сети: " + t.getMessage());
            }
        });
    }

    public interface ApplicationDetailsCallback {
        void onApplicationDetailsLoaded(ResponseApplication applicationDetails);
        void onError(String errorMessage);
    }
}
