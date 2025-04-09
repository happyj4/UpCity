package com.example.upcity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class AnimationUtilsHelper {

    public static void animateAndNavigate(Activity activity, int layoutId, int animationResId,
                                          Class<?> targetActivity, Intent intent) {
        LinearLayout linearLayout = activity.findViewById(layoutId);
        Animation animation = AnimationUtils.loadAnimation(activity, animationResId);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                if (targetActivity != null) {
                    linearLayout.setVisibility(View.INVISIBLE);

                    if (intent != null) {
                        intent.setClass(activity, targetActivity);
                        activity.startActivity(intent);
                    } else {
                        Intent defaultIntent = new Intent(activity, targetActivity);
                        activity.startActivity(defaultIntent);
                    }

                    activity.overridePendingTransition(0, 0);
                    activity.finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        linearLayout.startAnimation(animation);
    }
}
