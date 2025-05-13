package com.example.upcity.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.upcity.network.ApiService;
import com.example.upcity.network.RetrofitClient;
import com.example.upcity.utils.UserRequest;
import com.example.upcity.utils.ApiResponse;

import org.json.JSONArray;
import org.json.JSONObject;

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

                    String email = userRequest.getEmail();
                    String name = response.body().getUser().getName();
                    String surname = response.body().getUser().getSurname();
                    String access_token = response.body().getAccessToken();

                    saveUser(context, email, name, surname, access_token);
                } else {
                    String errorMessage = parseErrorMessage(response);
                    callback.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    private String parseErrorMessage(Response<?> response) {
        try {
            String errorBody = response.errorBody().string();
            JSONObject json = new JSONObject(errorBody);
            Object detail = json.get("detail");

            if (detail instanceof JSONArray) {
                return ((JSONArray) detail).getJSONObject(0).getString("msg");
            }

            return detail.toString();
        } catch (Exception e) {
            return "Помилка";
        }
    }

    public interface RegistrationCallback {
        void onSuccess(String message);
        void onFailure(String error);
    }

    public static void saveUser(Context context, String email, String name, String surname, String access_token) {
        SharedPreferences prefs = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email);
        editor.putString("name", name);
        editor.putString("surname", surname);
        editor.putString("access_token", access_token);
        editor.apply();
    }
}
