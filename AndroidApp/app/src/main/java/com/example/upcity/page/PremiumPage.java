package com.example.upcity.page;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.upcity.adapters.AdapterAnimation;
import com.example.upcity.R;
import com.example.upcity.adapters.FragmentToolbar;
import com.example.upcity.helpers.GooglePay;
import com.example.upcity.utils.ResponseGooglePay;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;

public class PremiumPage extends AppCompatActivity {

    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;
    private GooglePay googlePay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);
        AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new FragmentToolbar())
                    .commit();
        }

        googlePay = new GooglePay(this);
        googlePay.initGooglePay();

        // Подключение зависимостей
        Button BuyPremiumButton = findViewById(R.id.BuyPremiumButton);
        Button HomeButton = findViewById(R.id.HomeButton);

        // Подключение кнопок
        HomeButton.setOnClickListener(view -> {
            AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        });

        BuyPremiumButton.setOnClickListener(view -> {
            googlePay.startPayment(this, LOAD_PAYMENT_DATA_REQUEST_CODE);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                PaymentData paymentData = PaymentData.getFromIntent(data);
                if (paymentData != null) {
                    googlePay.handlePaymentSuccess(paymentData, new GooglePay.PaymentCallback() {
                        @Override
                        public void onSuccess(ResponseGooglePay response) {
                            runOnUiThread(() -> {
                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                    messageSend("Підписка активована!", response.message);
                                }, 1000);
                            });
                        }

                        @Override
                        public void onError(String errorMessage) {
                            runOnUiThread(() -> {
                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                    messageSend("Помилка!", errorMessage);
                                }, 1000);
                            });
                        }
                    });
                }
            } else if (resultCode == RESULT_CANCELED) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    messageSend("Помилка!", "Спробуйте ще раз");
                }, 1000);
            } else if (resultCode == AutoResolveHelper.RESULT_ERROR) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    messageSend("Помилка!", "Спробуйте пізніше");
                }, 1000);
            }
        }
    }


    protected void messageSend(String name, String description) {
        Intent intent = new Intent(PremiumPage.this, MessagePage.class);

        intent.putExtra("name", name);
        intent.putExtra("description", description);

        AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_left, MessagePage.class, intent);
    }

    //Изменяет кнопку назад
    @Override
    public void onBackPressed() {
        AdapterAnimation.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
    }
}