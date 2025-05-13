package com.example.upcity.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upcity.R;
import com.example.upcity.page.CreateApplicationPage;
import com.example.upcity.page.ViewApplicationPage;
import com.example.upcity.utils.ApplicationRequest;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ApplicationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_APPLICATION = 1;

    private List<ApplicationRequest> applicationList;

    public ApplicationAdapter(List<ApplicationRequest> applicationList) {
        this.applicationList = applicationList;
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
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_application, parent, false);
            return new ApplicationViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ApplicationRequest applicationRequest = applicationList.get(position);

        if (holder instanceof ApplicationViewHolder) {
            ApplicationViewHolder appHolder = (ApplicationViewHolder) holder;
            appHolder.applicationNumber.setText(String.valueOf(applicationRequest.getApplicationNumber()));
            appHolder.applicationName.setText(applicationRequest.getName());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            appHolder.applicationDate.setText(dateFormat.format(applicationRequest.getApplicationDate()));

            appHolder.applicationUtilityCompany.setText(String.valueOf(applicationRequest.getUtilityCompany().getName()));

            if (applicationRequest.getStatus().equals("Виконано")) {
                appHolder.applicationStatus.setImageResource(R.drawable.completed_application);
            } else if (applicationRequest.getStatus().equals("В роботі")) {
                appHolder.applicationStatus.setImageResource(R.drawable.work_application);
            } else {
                appHolder.applicationStatus.setImageResource(R.drawable.rejected_application);
            }
        }

        holder.itemView.setOnClickListener(v -> {
            if (applicationRequest != null) {
            Intent intent = new Intent(v.getContext(), ViewApplicationPage.class);

            intent.putExtra("applicationId", applicationRequest.getApplicationId());

            AnimationUtilsHelper.animateAndNavigate(((Activity) v.getContext()), R.id.linearLayout, R.anim.slide_out_left, ViewApplicationPage.class, intent);
            }
            else {
                AnimationUtilsHelper.animateAndNavigate(((Activity) v.getContext()), R.id.linearLayout, R.anim.slide_out_left, CreateApplicationPage.class, null);
            }
        });
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