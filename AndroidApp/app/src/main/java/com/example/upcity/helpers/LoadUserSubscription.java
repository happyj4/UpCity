package com.example.upcity.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.upcity.network.ApiService;
import com.example.upcity.network.RetrofitClient;
import com.example.upcity.utils.ResponseUserSubscription;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadUserSubscription {
    public static void loadUserSubscription(Context context, UserSubscriptionCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access_token", null);

        ApiService apiService = RetrofitClient.getInstance();
        Call<ResponseUserSubscription> call = apiService.getUserSubscription("Bearer " + accessToken);

        call.enqueue(new Callback<ResponseUserSubscription>() {
            @Override
            public void onResponse(Call<ResponseUserSubscription> call, Response<ResponseUserSubscription> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSubscriptionLoaded(response.body());
                } else {
                    callback.onError("Ошибка загрузки: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseUserSubscription> call, Throwable t) {
                callback.onError("Ошибка сети: " + t.getMessage());
            }
        });
    }

    public interface UserSubscriptionCallback {
        void onSubscriptionLoaded(ResponseUserSubscription subscription);
        void onError(String errorMessage);
    }
}
