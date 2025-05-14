package com.example.upcity.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.upcity.network.ApiService;
import com.example.upcity.network.RetrofitClient;
import com.example.upcity.utils.RequestCreateApplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadUserApplications {
    public void getUserApplications(Context context, final ApplicationCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access_token", null);

        ApiService apiService = RetrofitClient.getInstance();
        Call<List<RequestCreateApplication>> call = apiService.getUserApplications("Bearer " + accessToken);

        call.enqueue(new Callback<List<RequestCreateApplication>>() {
            @Override
            public void onResponse(Call<List<RequestCreateApplication>> call, Response<List<RequestCreateApplication>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<RequestCreateApplication>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface ApplicationCallback {
        void onSuccess(List<RequestCreateApplication> applications);
        void onFailure(String error);
    }
}
