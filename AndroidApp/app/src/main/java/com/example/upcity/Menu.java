package com.example.upcity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class Menu {

    private PopupWindow popupWindow;

    public void showPopupMenu(View anchorView, Context context) {
        View popupView = LayoutInflater.from(context).inflate(R.layout.menu, null);

        popupWindow = new PopupWindow(popupView, 490, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setElevation(7);
        popupWindow.showAsDropDown(anchorView, -390, 50);

        popupView.findViewById(R.id.EditProfileButton).setOnClickListener(v -> {
            popupWindow.dismiss();
            Intent intent = new Intent(context, EditProfilePage.class);
            context.startActivity(intent);

            if (context instanceof Activity) {
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, 0);
            }
        });

        popupView.findViewById(R.id.MyApplicationButton).setOnClickListener(v -> {
            popupWindow.dismiss();
            Intent intent = new Intent(context, MyApplicationPage.class);
            context.startActivity(intent);

            if (context instanceof Activity) {
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, 0);
            }
        });

        popupView.findViewById(R.id.AllApplicationButton).setOnClickListener(v -> {
            popupWindow.dismiss();
            Intent intent = new Intent(context, AllApplicationPage.class);
            context.startActivity(intent);

            if (context instanceof Activity) {
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, 0);
            }
        });
        popupView.findViewById(R.id.PremiumButton).setOnClickListener(v -> {
            popupWindow.dismiss();
            Intent intent = new Intent(context, BuyPremiumPage.class);
            context.startActivity(intent);

            if (context instanceof Activity) {
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, 0);
            }
        });

        popupView.findViewById(R.id.ExitButton).setOnClickListener(v -> {
            popupWindow.dismiss();
            Toast.makeText(context, "Вийти", Toast.LENGTH_SHORT).show();
        });
    }
}
