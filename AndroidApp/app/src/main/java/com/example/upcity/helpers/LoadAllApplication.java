package com.example.upcity.helpers;

import android.content.Context;

import com.example.upcity.network.ApiService;
import com.example.upcity.network.RetrofitClient;
import com.example.upcity.utils.ResponseApplication;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadAllApplication {
    public void loadApplications(Context context, String selectedSortFilter, String selectedDateFilter, String selectedStatusFilter, final ApplicationCallback callback) {
        ApiService apiService = RetrofitClient.getInstance();
        Call<List<ResponseApplication>> call = apiService.getApplications(
                selectedSortFilter,
                selectedDateFilter,
                selectedStatusFilter
        );

        call.enqueue(new Callback<List<ResponseApplication>>() {
            @Override
            public void onResponse(Call<List<ResponseApplication>> call, Response<List<ResponseApplication>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ResponseApplication> allApplications = response.body();

                    List<ResponseApplication> filteredApplications = new ArrayList<>();
                    for (ResponseApplication app : allApplications) {
                        if (!"Не розглянута".equalsIgnoreCase(app.getStatus())) {
                            filteredApplications.add(app);
                        }
                    }

                    callback.onSuccess(filteredApplications);
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

