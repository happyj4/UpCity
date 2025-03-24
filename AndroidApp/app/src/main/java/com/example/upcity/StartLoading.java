package com.example.upcity;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import androidx.appcompat.app.AppCompatActivity;


public class StartLoading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_loading);

        final android.view.View contentView = findViewById(android.R.id.content);

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        contentView.startAnimation(fadeIn);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation fadeOut = AnimationUtils.loadAnimation(StartLoading.this, R.anim.fade_out);
                contentView.startAnimation(fadeOut);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(StartLoading.this, LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    }
                }, 1000);
            }
        }, 2000);
    }
}