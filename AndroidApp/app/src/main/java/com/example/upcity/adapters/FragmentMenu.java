package com.example.upcity.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.upcity.R;
import com.example.upcity.page.AllApplicationPage;
import com.example.upcity.page.EditProfilePage;
import com.example.upcity.page.LoginPage;
import com.example.upcity.page.MyApplicationPage;
import com.example.upcity.page.PremiumPage;

public class FragmentMenu {

    private PopupWindow popupWindow;

    public void showPopupMenu(View anchorView, Context context) {
        View popupView = LayoutInflater.from(context).inflate(R.layout.view_menu, null);

        popupWindow = new PopupWindow(popupView, 490, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setElevation(7);
        popupWindow.showAsDropDown(anchorView, -390, 50);

        String currentActivity = context.getClass().getSimpleName();

        TextView editBtn = popupView.findViewById(R.id.EditProfileButton);
        TextView myAppBtn = popupView.findViewById(R.id.MyApplicationButton);
        TextView allAppBtn = popupView.findViewById(R.id.AllApplicationButton);
        TextView premiumBtn = popupView.findViewById(R.id.PremiumButton);
        TextView exitBtn = popupView.findViewById(R.id.ExitButton);

        int selectedColor = Color.parseColor("#FBF0E5");
        int defaultColor = Color.parseColor("#FFFFFF");

        editBtn.setBackgroundColor("EditProfilePage".equals(currentActivity) ? selectedColor : defaultColor);
        myAppBtn.setBackgroundColor("MyApplicationPage".equals(currentActivity) ? selectedColor : defaultColor);
        allAppBtn.setBackgroundColor("AllApplicationPage".equals(currentActivity) ? selectedColor : defaultColor);
        premiumBtn.setBackgroundColor("PremiumPage".equals(currentActivity) ? selectedColor : defaultColor);

        editBtn.setOnClickListener(v -> {
            popupWindow.dismiss();
            if (context instanceof Activity && !"EditProfilePage".equals(currentActivity)) {
                AdapterAnimation.animateAndNavigate((Activity) context, R.id.linearLayout, R.anim.slide_out_left, EditProfilePage.class, null);
            }
        });

        myAppBtn.setOnClickListener(v -> {
            popupWindow.dismiss();
            if (context instanceof Activity && !"MyApplicationPage".equals(currentActivity)) {
                AdapterAnimation.animateAndNavigate((Activity) context, R.id.linearLayout, R.anim.slide_out_left, MyApplicationPage.class, null);
            }
        });

        allAppBtn.setOnClickListener(v -> {
            popupWindow.dismiss();
            if (context instanceof Activity && !"AllApplicationPage".equals(currentActivity)) {
                AdapterAnimation.animateAndNavigate((Activity) context, R.id.linearLayout, R.anim.slide_out_left, AllApplicationPage.class, null);
            }
        });

        premiumBtn.setOnClickListener(v -> {
            popupWindow.dismiss();
            if (context instanceof Activity && !"PremiumPage".equals(currentActivity)) {
                AdapterAnimation.animateAndNavigate((Activity) context, R.id.linearLayout, R.anim.slide_out_left, PremiumPage.class, null);
            }
        });

        exitBtn.setOnClickListener(v -> {
            popupWindow.dismiss();
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                Intent intent = new Intent(activity, LoginPage.class);
                intent.putExtra("skipAnimation", true);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_out, 0);
                activity.finish();
            }
        });
    }
}
