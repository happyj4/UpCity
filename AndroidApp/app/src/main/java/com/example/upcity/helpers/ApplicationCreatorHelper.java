package com.example.upcity.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.upcity.network.ApiService;
import com.example.upcity.network.RetrofitClient;
import com.example.upcity.utils.ApiResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationCreatorHelper {

    public static void createApplication(Context context, String name, String address, String description, String companyName, File photo, final ApplicationCreationCallback callback) {
        ApiService apiService = RetrofitClient.getInstance();
        SharedPreferences prefs = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access_token", null);
        RequestBody namePart = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody addressPart = RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody descriptionPart = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody companyNamePart = RequestBody.create(MediaType.parse("text/plain"), companyName);

        RequestBody photoPart = RequestBody.create(MediaType.parse("image/*"), photo);
        MultipartBody.Part photoFile = MultipartBody.Part.createFormData("photo", photo.getName(), photoPart);

        Call<ApiResponse> call = apiService.createApplication("Bearer " + accessToken, namePart, addressPart, descriptionPart, companyNamePart, photoFile);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null) {
                        callback.onApplicationCreated(apiResponse.getMessage());
                    } else {
                        callback.onError("Ответ пустой");
                    }
                } else {
                    callback.onError("Ошибка создания заявки: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                callback.onError("Ошибка сети: " + t.getMessage());
            }
        });
    }


    public interface ApplicationCreationCallback {
        void onApplicationCreated(String message);
        void onError(String errorMessage);
    }
}
