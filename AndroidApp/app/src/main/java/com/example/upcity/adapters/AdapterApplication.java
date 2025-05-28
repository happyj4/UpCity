package com.example.upcity.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.upcity.R;
import com.example.upcity.page.CreateApplicationPage;
import com.example.upcity.page.ViewApplicationPage;
import com.example.upcity.utils.ResponseApplication;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AdapterApplication extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_APPLICATION = 1;

    private List<ResponseApplication> applicationList;
    private int lastPosition = -1;
    private boolean[] animationCompleted;
    private boolean animationsAlreadyPlayed = false;

    private boolean isHorizontal;

    public AdapterApplication(List<ResponseApplication> applicationList, boolean isHorizontal) {
        this.applicationList = applicationList;
        this.isHorizontal = isHorizontal;
        animationCompleted = new boolean[applicationList.size()];
    }

    @Override
    public int getItemViewType(int position) {
        return applicationList.get(position) == null ? TYPE_EMPTY : TYPE_APPLICATION;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_empty, parent, false);
            return new EmptyViewHolder(view);
        } else {
            View view;
            if (isHorizontal) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_application_horizontal, parent, false);
            } else {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_application_vertical, parent, false);
            }
            return new ApplicationViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ResponseApplication responseApplication = applicationList.get(position);

        if (holder instanceof ApplicationViewHolder) {
            ApplicationViewHolder appHolder = (ApplicationViewHolder) holder;
            appHolder.applicationNumber.setText("#" + responseApplication.getApplication_id());
            appHolder.applicationName.setText(responseApplication.getName());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            appHolder.applicationDate.setText(dateFormat.format(responseApplication.getApplication_date()));

            appHolder.applicationUtilityCompany.setText(String.valueOf(responseApplication.getUtility_company().getName()));

            switch (responseApplication.getStatus()) {
                case "Виконано":
                    appHolder.applicationStatus.setImageResource(R.drawable.status_complete);
                    break;
                case "В роботі":
                    appHolder.applicationStatus.setImageResource(R.drawable.status_work);
                    break;
                case "Не розглянута":
                    appHolder.applicationStatus.setImageResource(R.drawable.status_waiting);
                    break;
                default:
                    appHolder.applicationStatus.setImageResource(R.drawable.status_rejected);
                    break;
            }
        }

        holder.itemView.setOnClickListener(v -> {
            if (responseApplication != null) {
                Intent intent = new Intent(v.getContext(), ViewApplicationPage.class);
                intent.putExtra("applicationId", responseApplication.getApplication_id());
                intent.putExtra("activity", v.getContext().getClass().getSimpleName());
                AdapterAnimation.animateAndNavigate((Activity) v.getContext(), R.id.linearLayout, R.anim.slide_out_left, ViewApplicationPage.class, intent);
            } else {
                AdapterAnimation.animateAndNavigate((Activity) v.getContext(), R.id.linearLayout, R.anim.slide_out_left, CreateApplicationPage.class, null);
            }
        });

        if (!animationCompleted[position]) {
            setAnimation(holder.itemView, position);
            animationCompleted[position] = true;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        recyclerView.postDelayed(() -> animationsAlreadyPlayed = true, 400);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (animationsAlreadyPlayed) return;

        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.item_fade_in);
            animation.setStartOffset(position * 65);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    public static class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder {
        TextView applicationNumber;
        TextView applicationName;
        TextView applicationDate;
        ImageView applicationStatus;
        TextView applicationUtilityCompany;

        public ApplicationViewHolder(View itemView) {
            super(itemView);
            applicationNumber = itemView.findViewById(R.id.ApplicationNumber);
            applicationName = itemView.findViewById(R.id.ApplicationName);
            applicationStatus = itemView.findViewById(R.id.ApplicationStatus);
            applicationDate = itemView.findViewById(R.id.ApplicationDate);
            applicationUtilityCompany = itemView.findViewById(R.id.ApplicationUtilityCompany);
        }
    }
}
