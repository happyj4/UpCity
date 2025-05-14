package com.example.upcity.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.upcity.R;
import com.example.upcity.page.CreateApplicationPage;
import com.example.upcity.page.ViewApplicationPage;
import com.google.android.material.imageview.ShapeableImageView;

public class FragmentToolbar extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_custom_toolbar, container, false);

        // Подключение зависимостей
        TextView NameText = view.findViewById(R.id.NameText);
        ShapeableImageView photoButton = view.findViewById(R.id.PhotoButton);
        ImageButton plusButton = view.findViewById(R.id.PlusButton);
        FragmentMenu fragmentMenu = new FragmentMenu();

        // Изменение данных пользователя
        NameText.setText(getUserInfo(requireContext()));

        String imageUrl = (getImage(requireContext()));
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(FragmentToolbar.this)
                    .load(imageUrl)
                    .into(photoButton);
        }

        // Подключение кнопок
        photoButton.setOnClickListener(v -> {
            if (requireActivity() != null) {
                fragmentMenu.showPopupMenu(v, requireActivity());
            }
        });

        plusButton.setOnClickListener(v -> {
            AdapterAnimation.animateAndNavigate(  requireActivity(), R.id.linearLayout, R.anim.slide_out_left, CreateApplicationPage.class, null);
        });

        return view;
    }

    public static String getUserInfo(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String name = prefs.getString("name", null);
        String surname = prefs.getString("surname", null);

        return surname + " " + name;
    }

    public static String getImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);

        return prefs.getString("image", null);
    }
}
