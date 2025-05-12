package com.example.upcity.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.upcity.network.ApiService;
import com.example.upcity.network.RetrofitClient;
import com.example.upcity.utils.ApiLoginResponse;
import com.example.upcity.utils.LoginRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginHelper {

    public void login(Context context, LoginRequest loginRequest, final LoginCallback callback) {
        ApiService apiService = RetrofitClient.getInstance();

        Call<ApiLoginResponse> call = apiService.login(loginRequest);

        call.enqueue(new Callback<ApiLoginResponse>() {
            @Override
            public void onResponse(Call<ApiLoginResponse> call, Response<ApiLoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                    String name = response.body().getData().getName();
                    String surname = response.body().getData().getSurname();

                    saveUser(context, loginRequest.getEmail(), name, surname);
                } else {
                    callback.onFailure("Login failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiLoginResponse> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    public interface LoginCallback {
        void onSuccess(String token);
        void onFailure(String error);
    }

    public static void saveUser(Context context, String email, String name, String surname) {
        SharedPreferences prefs = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email);
        editor.putString("name", name);
        editor.putString("surname", surname);
        editor.apply();
    }
}

