package com.example.upcity.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.upcity.network.ApiService;
import com.example.upcity.network.RetrofitClient;
import com.example.upcity.utils.ResponseApplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadUserApplications {
    public void getUserApplications(Context context, String selectedSortFilter, String selectedDateFilter, String selectedStatusFilter, final ApplicationCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access_token", null);

        ApiService apiService = RetrofitClient.getInstance();
        Call<List<ResponseApplication>> call = apiService.getUserApplications(
                "Bearer " + accessToken,
                selectedSortFilter,
                selectedDateFilter,
                selectedStatusFilter
        );

        call.enqueue(new Callback<List<ResponseApplication>>() {
            @Override
            public void onResponse(Call<List<ResponseApplication>> call, Response<List<ResponseApplication>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseApplication>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface ApplicationCallback {
        void onSuccess(List<ResponseApplication> applications);
        void onFailure(String error);
    }
}
