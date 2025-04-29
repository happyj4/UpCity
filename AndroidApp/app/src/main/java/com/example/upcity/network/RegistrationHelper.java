package com.example.upcity.network;

import android.content.Context;
import com.example.upcity.utils.User;
import com.example.upcity.utils.ApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationHelper {

    public void registerUser(Context context, User user, final RegistrationCallback callback) {
        ApiService apiService = RetrofitClient.getInstance();
        Call<ApiResponse> call = apiService.createUser(user);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
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
}
