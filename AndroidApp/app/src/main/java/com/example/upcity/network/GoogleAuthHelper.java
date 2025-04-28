package com.example.upcity.network;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.upcity.R;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class GoogleAuthHelper {
    private final Activity activity;
    private final GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private OnGoogleAuthListener authListener;

    public GoogleAuthHelper(Activity activity) {
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
                    authListener.onSuccess(account.getDisplayName(), account.getEmail());
                }
            } catch (ApiException e) {
                if (authListener != null) {
                    authListener.onFailure(e);
                }
            }
        }
    }

    public interface OnGoogleAuthListener {
        void onSuccess(String name, String email);
        void onFailure(Exception e);
    }
}
