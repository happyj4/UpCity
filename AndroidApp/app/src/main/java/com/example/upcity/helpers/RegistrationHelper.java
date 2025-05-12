package com.example.upcity.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.upcity.network.ApiService;
import com.example.upcity.network.RetrofitClient;
import com.example.upcity.utils.UserRequest;
import com.example.upcity.utils.ApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationHelper {

    public void registerUser(Context context, UserRequest userRequest, final RegistrationCallback callback) {
        ApiService apiService = RetrofitClient.getInstance();
        Call<ApiResponse> call = apiService.createUser(userRequest);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                    saveUser(context, userRequest.getEmail(), userRequest.getName(), userRequest.getSurname());
                } else {
                    callback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface RegistrationCallback {
        void onSuccess(String message);
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
