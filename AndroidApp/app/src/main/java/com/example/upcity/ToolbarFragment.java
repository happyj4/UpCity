package com.example.upcity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import com.google.android.material.imageview.ShapeableImageView;

public class ToolbarFragment extends Fragment {

    private ShapeableImageView photoButton;
    private ImageButton plusButton;
    private Menu menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_toolbar, container, false);

        photoButton = view.findViewById(R.id.PhotoButton);
        plusButton = view.findViewById(R.id.PlusButton);

        menu = new Menu();

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
}
