package com.example.upcity.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.upcity.R;
import com.example.upcity.page.AllApplicationPage;
import com.example.upcity.page.PremiumPage;
import com.example.upcity.page.EditProfilePage;
import com.example.upcity.page.MyApplicationPage;

public class Menu {

    private PopupWindow popupWindow;

    public void showPopupMenu(View anchorView, Context context) {
        View popupView = LayoutInflater.from(context).inflate(R.layout.view_menu, null);

        popupWindow = new PopupWindow(popupView, 490, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setElevation(7);
        popupWindow.showAsDropDown(anchorView, -390, 50);

        popupView.findViewById(R.id.EditProfileButton).setOnClickListener(v -> {
            popupWindow.dismiss();
            if (context instanceof Activity) {
                AnimationUtilsHelper.animateAndNavigate((Activity) context, R.id.linearLayout, R.anim.slide_out_left, EditProfilePage.class, null);
            }
        });

        popupView.findViewById(R.id.MyApplicationButton).setOnClickListener(v -> {
            popupWindow.dismiss();
            if (context instanceof Activity) {
                AnimationUtilsHelper.animateAndNavigate((Activity) context, R.id.linearLayout, R.anim.slide_out_left, MyApplicationPage.class, null);
            }
        });

        popupView.findViewById(R.id.AllApplicationButton).setOnClickListener(v -> {
            popupWindow.dismiss();
            if (context instanceof Activity) {
                AnimationUtilsHelper.animateAndNavigate((Activity) context, R.id.linearLayout, R.anim.slide_out_left, AllApplicationPage.class, null);
            }
        });
        popupView.findViewById(R.id.PremiumButton).setOnClickListener(v -> {
            popupWindow.dismiss();
            if (context instanceof Activity) {
                AnimationUtilsHelper.animateAndNavigate((Activity) context, R.id.linearLayout, R.anim.slide_out_left, PremiumPage.class, null);
            }
        });

        popupView.findViewById(R.id.ExitButton).setOnClickListener(v -> {
            popupWindow.dismiss();
            Toast.makeText(context, "Вийти", Toast.LENGTH_SHORT).show();
        });
    }
}
