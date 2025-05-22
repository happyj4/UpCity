package com.example.upcity.page;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import com.example.upcity.R;


public class StartLoading extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        final android.view.View contentView = findViewById(android.R.id.content);

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        contentView.startAnimation(fadeIn);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartLoading.this, LoginPage.class);
                intent.putExtra("skipAnimation", true);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, 0);
                finish();
            }
        }, 3000);
    }

    //Изменяет кнопку назад
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            if (backToast != null) backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Якщо хочеш вийти, натисни ще раз", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}