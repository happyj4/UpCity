package com.example.upcity.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.upcity.network.ApiService;
import com.example.upcity.network.RetrofitClient;
import com.example.upcity.utils.ResponseAuthentication;
import com.example.upcity.utils.RequestLogin;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelperLogin {

    public void login(Context context, RequestLogin requestLogin, final LoginCallback callback) {
        ApiService apiService = RetrofitClient.getInstance();

        Call<ResponseAuthentication> call = apiService.login(
                requestLogin.getGrantType(),
                requestLogin.getUsername(),
                requestLogin.getPassword(),
                requestLogin.getScope(),
                requestLogin.getClientId(),
                requestLogin.getClientSecret()
        );

        call.enqueue(new Callback<ResponseAuthentication>() {
            @Override
            public void onResponse(Call<ResponseAuthentication> call, Response<ResponseAuthentication> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());

                    String name = response.body().getUser().getName();
                    String surname = response.body().getUser().getSurname();
                    String email = requestLogin.getUsername();
                    String access_token = response.body().getAccessToken();
                    String image = response.body().getUser().getImage();

                    saveUser(context, email, name, surname, access_token, image);
                } else {
                    String errorMessage = parseErrorMessage(response);
                    callback.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ResponseAuthentication> call, Throwable t) {
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

    public interface LoginCallback {
        void onSuccess(String token);
        void onFailure(String error);
    }

    public static void saveUser(Context context, String email, String name, String surname, String access_token, String image) {
        SharedPreferences prefs = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email);
        editor.putString("name", name);
        editor.putString("surname", surname);
        editor.putString("access_token", access_token);
        editor.putString("image", image);
        editor.apply();
    }
}

