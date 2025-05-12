package com.example.upcity.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.upcity.R;
import com.example.upcity.page.CreateApplicationPage;
import com.google.android.material.imageview.ShapeableImageView;

public class ToolbarFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_custom_toolbar, container, false);

        TextView NameText = view.findViewById(R.id.NameText);
        ShapeableImageView photoButton = view.findViewById(R.id.PhotoButton);
        ImageButton plusButton = view.findViewById(R.id.PlusButton);
        Menu menu = new Menu();

        NameText.setText(getUserInfo(requireContext()));

        photoButton.setOnClickListener(v -> {
            if (requireActivity() != null) {
                menu.showPopupMenu(v, requireActivity());
            }
        });

        plusButton.setOnClickListener(v -> {
            AnimationUtilsHelper.animateAndNavigate(  requireActivity(), R.id.linearLayout, R.anim.slide_out_left, CreateApplicationPage.class, null);
        });

        return view;
    }

    public static String getUserInfo(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String name = prefs.getString("name", null);
        String surname = prefs.getString("surname", null);

        return surname + " " + name;
    }
}
