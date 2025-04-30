package com.example.upcity.page;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upcity.adapters.AnimationUtilsHelper;
import com.example.upcity.utils.ApplicationRequest;
import com.example.upcity.adapters.ApplicationAdapter;
import com.example.upcity.R;
import com.example.upcity.adapters.ToolbarFragment;

import java.util.ArrayList;
import java.util.List;

public class MyApplicationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_applications);
        AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_in_right, null, null);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_container, new ToolbarFragment())
                    .commit();
        }

        RecyclerView AllList = findViewById(R.id.AllList);
        Button HomeButton = findViewById(R.id.HomeButton);

        HomeButton.setOnClickListener(view -> {
            AnimationUtilsHelper.animateAndNavigate(this, R.id.linearLayout, R.anim.slide_out_right, HomePage.class, null);
        });

        GridLayoutManager gridLayoutManagerAllList = new GridLayoutManager(this, 2);
        AllList.setLayoutManager(gridLayoutManagerAllList);

        List<ApplicationRequest> applicationList = new ArrayList<>();
        // сюда все плашки

        applicationList.add(0, null);

        ApplicationAdapter adapter = new ApplicationAdapter(applicationList);

        AllList.setAdapter(adapter);
    }
}