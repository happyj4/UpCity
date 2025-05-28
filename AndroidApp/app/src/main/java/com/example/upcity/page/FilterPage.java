package com.example.upcity.page;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.upcity.R;

public class FilterPage extends AppCompatActivity {

    private  Button ConfirmButton;
    private  ImageView CloseButton;

    private TextView AZButton;
    private TextView ZAButton;

    private TextView NewButton;
    private TextView OldButton;

    private TextView InProgressButton;
    private TextView DoneButton;
    private TextView RejectedButton;
    private TextView WaitingButton;

    private String activity;

    private String selectedSortFilter = null;
    private String selectedDateFilter = null;
    private String selectedStatusFilter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        // Принимаю данные прошлой активности
        Intent intent = getIntent();
        activity = intent.getStringExtra("Activity");

        // Подключение зависимостей
        ConfirmButton = findViewById(R.id.ConfirmButton);
        CloseButton = findViewById(R.id.CloseButton);

        AZButton = findViewById(R.id.AZButton);
        ZAButton = findViewById(R.id.ZAButton);

        NewButton = findViewById(R.id.NewButton);
        OldButton = findViewById(R.id.OldButton);

        InProgressButton = findViewById(R.id.InProgressButton);
        DoneButton = findViewById(R.id.DoneButton);
        RejectedButton = findViewById(R.id.RejectedButton);
        WaitingButton = findViewById(R.id.WaitingButton);

        CloseButton.setOnClickListener(view -> {
            if ("AllApplicationPage".equals(activity)) {
                Intent Intent = new Intent(this, AllApplicationPage.class);
                Intent.putExtra("skipAnimation", true);
                startActivity(Intent);
                overridePendingTransition(R.anim.slide_in_out, 0);
                finish();
            } else if ("MyApplicationPage".equals(activity)) {
                Intent Intent = new Intent(this, MyApplicationPage.class);
                Intent.putExtra("skipAnimation", true);
                startActivity(Intent);
                overridePendingTransition(R.anim.slide_in_out, 0);
                finish();
            }
        });

        ConfirmButton.setOnClickListener(view -> {
            if ("AllApplicationPage".equals(activity)) {
                Intent Intent = new Intent(this, AllApplicationPage.class);
                Intent.putExtra("skipAnimation", true);

                Intent.putExtra("selectedSortFilter", selectedSortFilter);
                Intent.putExtra("selectedDateFilter", selectedDateFilter);
                Intent.putExtra("selectedStatusFilter", selectedStatusFilter);

                startActivity(Intent);
                overridePendingTransition(R.anim.slide_in_out, 0);
                finish();
            } else if ("MyApplicationPage".equals(activity)) {
                Intent Intent = new Intent(this, MyApplicationPage.class);
                Intent.putExtra("skipAnimation", true);

                Intent.putExtra("selectedSortFilter", selectedSortFilter);
                Intent.putExtra("selectedDateFilter", selectedDateFilter);
                Intent.putExtra("selectedStatusFilter", selectedStatusFilter);

                startActivity(Intent);
                overridePendingTransition(R.anim.slide_in_out, 0);
                finish();
            }
        });

        // Сортировка
        AZButton.setOnClickListener(v -> toggleFilter(AZButton, "sort", selectedSortFilter));
        ZAButton.setOnClickListener(v -> toggleFilter(ZAButton, "sort", selectedSortFilter));

        // Дата
        NewButton.setOnClickListener(v -> toggleFilter(NewButton, "date", selectedDateFilter));
        OldButton.setOnClickListener(v -> toggleFilter(OldButton, "date", selectedDateFilter));

        // Статус
        InProgressButton.setOnClickListener(v -> toggleFilter(InProgressButton, "status", selectedStatusFilter));
        DoneButton.setOnClickListener(v -> toggleFilter(DoneButton, "status", selectedStatusFilter));
        RejectedButton.setOnClickListener(v -> toggleFilter(RejectedButton, "status", selectedStatusFilter));
        WaitingButton.setOnClickListener(v -> toggleFilter(WaitingButton, "status", selectedStatusFilter));

        // Изначально все серые
        setButtonsColor("#3A3A3A", AZButton, ZAButton);
        setButtonsColor("#3A3A3A", NewButton, OldButton);
        setButtonsColor("#3A3A3A", InProgressButton, DoneButton, RejectedButton, WaitingButton);
    }

    private void toggleFilter(TextView clickedButton, String filterType, String currentSelected) {
        String clickedText = clickedButton.getText().toString();

        if (clickedText.equals("А - Я")) {
            clickedText = "А-Я";
        } else if (clickedText.equals("Я - А")) {
            clickedText = "Я-А";
        } else if (clickedText.equals("Очікує")) {
            clickedText = "Не розглянута";
        }

        if (clickedText.equals(currentSelected)) {
            setButtonsColor("#3A3A3A", getButtonsByFilterType(filterType));
            updateSelectedFilter(filterType, null);
        } else {
            setButtonsColor("#3A3A3A", getButtonsByFilterType(filterType));
            clickedButton.setTextColor(Color.parseColor("#FFBE7D"));
            updateSelectedFilter(filterType, clickedText);
        }
    }

    // Обновляем выбранный фильтр по типу
    private void updateSelectedFilter(String filterType, String value) {
        switch (filterType) {
            case "sort":
                selectedSortFilter = value;
                break;
            case "date":
                selectedDateFilter = value;
                break;
            case "status":
                selectedStatusFilter = value;
                break;
        }
    }

    // Возвращает массив кнопок по фильтру
    private TextView[] getButtonsByFilterType(String filterType) {
        switch (filterType) {
            case "sort":
                return new TextView[]{AZButton, ZAButton};
            case "date":
                return new TextView[]{NewButton, OldButton};
            case "status":
                return new TextView[]{InProgressButton, DoneButton, RejectedButton, WaitingButton};
            default:
                return new TextView[]{};
        }
    }

    // Устанавливаем цвет для всех кнопок
    private void setButtonsColor(String colorHex, TextView... buttons) {
        int color = Color.parseColor(colorHex);
        for (TextView btn : buttons) {
            btn.setTextColor(color);
        }
    }

    //Изменяет кнопку назад
    @Override
    public void onBackPressed() {
        if ("AllApplicationPage".equals(activity)) {
            Intent intent = new Intent(this, AllApplicationPage.class);
            intent.putExtra("skipAnimation", true);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_out, 0);
            finish();
        } else if ("MyApplicationPage".equals(activity)) {
            Intent intent = new Intent(this, MyApplicationPage.class);
            intent.putExtra("skipAnimation", true);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_out, 0);
            finish();
        }
    }
}

