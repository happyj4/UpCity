package com.example.upcity.page;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.upcity.adapters.AnimationUtilsHelper;
import com.example.upcity.R;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

public class LoginPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.upcity.R.layout.activity_login);

        boolean skipAnimation = getIntent().getBooleanExtra("skipAnimation", false);

        if (!skipAnimation) {
            AnimationUtilsHelper.animateAndNavigate(this, com.example.upcity.R.id.linearLayout, com.example.upcity.R.anim.slide_in_left, null, null);
        };

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(com.example.upcity.R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Button RegistrationButton = findViewById(com.example.upcity.R.id.RegistrationButton);
        Button LoginButton = findViewById(com.example.upcity.R.id.LoginButton);
        ImageButton GoogleButton = findViewById(com.example.upcity.R.id.GoogleButton);

        GoogleButton.setOnClickListener(view -> signInWithGoogle());

        RegistrationButton.setOnClickListener(view -> {
            AnimationUtilsHelper.animateAndNavigate(this, com.example.upcity.R.id.linearLayout, com.example.upcity.R.anim.slide_out_left, RegistrationPage.class, null);
        });

        LoginButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginPage.this, HomePage.class);
            intent.putExtra("skipAnimation", true);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_out, 0);
            finish();
        });
    }

    private void signInWithGoogle() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, 9001);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 9001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    String name = account.getDisplayName();
                    String email = account.getEmail();
                    Toast.makeText(this, "Вибраний акаунт:\nІм'я: " + name + "\nПошта: " + email, Toast.LENGTH_LONG).show();
                }
            } catch (ApiException e) {
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
