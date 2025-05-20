package com.example.upcity.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.upcity.network.ApiService;
import com.example.upcity.network.RetrofitClient;
import com.example.upcity.utils.ResponseUpdateProfile;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserInfo {

    public static void updateProfile(Context context, String name, String surname, String email, File photo, UpdateCallback callback) {
        ApiService apiService = RetrofitClient.getInstance();

        SharedPreferences prefs = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access_token", null);

        RequestBody namePart = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody surnamePart = RequestBody.create(MediaType.parse("text/plain"), surname);
        RequestBody emailPart = RequestBody.create(MediaType.parse("text/plain"), email);

        MultipartBody.Part photoFile = null;
        if (photo != null) {
            RequestBody photoPart = RequestBody.create(MediaType.parse("multipart/form-data"), photo);
            photoFile = MultipartBody.Part.createFormData("image", photo.getName(), photoPart);
        }

        Call<ResponseUpdateProfile> call = apiService.updateUser(
                emailPart,
                namePart,
                surnamePart,
                photoFile,
                "Bearer " + accessToken
        );

        call.enqueue(new Callback<ResponseUpdateProfile>() {
            @Override
            public void onResponse(Call<ResponseUpdateProfile> call, Response<ResponseUpdateProfile> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getUser());
                } else {
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseUpdateProfile> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public interface UpdateCallback {
        void onSuccess(ResponseUpdateProfile.User user);
        void onError(String error);
    }
}
