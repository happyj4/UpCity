package com.example.upcity.helpers;

import android.content.Context;

import com.example.upcity.network.ApiService;
import com.example.upcity.network.RetrofitClient;
import com.example.upcity.utils.ApplicationRequest;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationAllLoadHelper {
    public void loadApplications(Context context, final ApplicationCallback callback) {
        ApiService apiService = RetrofitClient.getInstance();
        Call<List<ApplicationRequest>> call = apiService.getApplications();

        call.enqueue(new Callback<List<ApplicationRequest>>() {
            @Override
            public void onResponse(Call<List<ApplicationRequest>> call, Response<List<ApplicationRequest>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ApplicationRequest>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface ApplicationCallback {
        void onSuccess(List<ApplicationRequest> applications);
        void onFailure(String error);
    }
}
