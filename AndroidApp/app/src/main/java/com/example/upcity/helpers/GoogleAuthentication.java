package com.example.upcity.helpers;

import static com.example.upcity.helpers.HelperLogin.saveUser;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.upcity.R;
import com.example.upcity.network.ApiService;
import com.example.upcity.network.RetrofitClient;
import com.example.upcity.utils.ResponseGoogleAuthentication;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class GoogleAuthentication {
    private final ApiService apiService = RetrofitClient.getInstance();

    private final Activity activity;
    private final GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private OnGoogleAuthListener authListener;

    public GoogleAuthentication(Activity activity) {
        this.activity = activity;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    public void setOnGoogleAuthListener(OnGoogleAuthListener listener) {
        this.authListener = listener;
    }

    public void signIn() {
        googleSignInClient.signOut().addOnCompleteListener(activity, task -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            activity.startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    public void handleActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null && authListener != null) {
                    String idToken = account.getIdToken();
                    Log.d("GoogleAuthToken", "Token: " + idToken);

                    Map<String, String> body = new HashMap<>();
                    body.put("id_token", idToken);

                    apiService.googleLogin(body).enqueue(new retrofit2.Callback<ResponseGoogleAuthentication>() {
                        @Override
                        public void onResponse(Call<ResponseGoogleAuthentication> call, retrofit2.Response<ResponseGoogleAuthentication> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                ResponseGoogleAuthentication responseBody = response.body();
                                authListener.onSuccess("Успішно");

                                String name = responseBody.user.name;
                                String surname = responseBody.user.surname;
                                String email =  account.getEmail();
                                String access_token = responseBody.access_token;
                                String image = responseBody.user.image;

                                saveUser(activity, email, name, surname, access_token, image);
                            } else {
                                String errorMessage = parseErrorMessage(response);
                                authListener.onFailure(errorMessage);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseGoogleAuthentication> call, Throwable t) {
                            authListener.onFailure("Network error: " + t.getMessage());
                        }
                    });
                }
            } catch (ApiException e) {
                if (authListener != null) {
                    authListener.onFailure(e.getMessage());
                }
            }
        }
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

    public interface OnGoogleAuthListener {
        void onSuccess(String success);
        void onFailure(String error);
    }
}
