package com.example.upcity.helpers;

import android.content.Context;

import com.example.upcity.network.ApiService;
import com.example.upcity.network.RetrofitClient;
import com.example.upcity.utils.UtilityCompanyRequest;

import java.util.List;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtilityCompanyAllLoadHelper {
    public void loadUtilityCompany(Context context, final ApplicationCallback callback) {
        ApiService apiService = RetrofitClient.getInstance();
        Call<List<UtilityCompanyRequest>> call = apiService.getUtilityCompany();

        call.enqueue(new Callback<List<UtilityCompanyRequest>>() {
            @Override
            public void onResponse(Call<List<UtilityCompanyRequest>> call, Response<List<UtilityCompanyRequest>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<UtilityCompanyRequest> companies = response.body();
                    List<String> companyNames = new ArrayList<>();

                    for (UtilityCompanyRequest company : companies) {
                        companyNames.add(company.getName());
                    }

                    callback.onSuccess(companyNames);
                } else {
                    callback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<UtilityCompanyRequest>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface ApplicationCallback {
        void onSuccess(List<String> companyNames);
        void onFailure(String error);
    }
}
