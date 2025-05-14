package com.example.upcity.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.upcity.network.ApiService;
import com.example.upcity.network.RetrofitClient;
import com.example.upcity.utils.RequestUtilityCompany;

import java.util.List;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadUtilityCompany {
    public void loadUtilityCompany(Context context, final ApplicationCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access_token", null);
        ApiService apiService = RetrofitClient.getInstance();
        Call<List<RequestUtilityCompany>> call = apiService.getUtilityCompany("Bearer " + accessToken);

        call.enqueue(new Callback<List<RequestUtilityCompany>>() {
            @Override
            public void onResponse(Call<List<RequestUtilityCompany>> call, Response<List<RequestUtilityCompany>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<RequestUtilityCompany> companies = response.body();
                    List<String> companyNames = new ArrayList<>();

                    for (RequestUtilityCompany company : companies) {
                        companyNames.add(company.getName());
                    }

                    callback.onSuccess(companyNames);
                } else {
                    callback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<RequestUtilityCompany>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface ApplicationCallback {
        void onSuccess(List<String> companyNames);
        void onFailure(String error);
    }
}
