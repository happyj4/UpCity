package com.example.upcity;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_APPLICATION = 1;

    private List<Application> applicationList;

    public ApplicationAdapter(List<Application> applicationList) {
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
                    .inflate(R.layout.empty_card, parent, false);
            return new EmptyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.application_card, parent, false);
            return new ApplicationViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Application application = applicationList.get(position);

        if (holder instanceof ApplicationViewHolder) {
            ApplicationViewHolder appHolder = (ApplicationViewHolder) holder;
            appHolder.applicationId.setText(String.valueOf(application.getId()));
            appHolder.applicationName.setText(application.getName());
            appHolder.applicationDate.setText(application.getCreationDate());
            appHolder.applicationKpid.setText(String.valueOf(application.getKpId()));
        }

        holder.itemView.setOnClickListener(v -> {
            if (application != null) {
            Intent intent = new Intent(v.getContext(), ViewApplicationPage.class);

            intent.putExtra("applicationId", application.getId());
            intent.putExtra("applicationName", application.getName());
            intent.putExtra("applicationDescription", application.getDescription());
            intent.putExtra("applicationAdress", application.getAddress());
            intent.putExtra("applicationDate", application.getCreationDate());
            intent.putExtra("applicationKpId", application.getKpId());
            intent.putExtra("applicationStatus", application.getStatus());
            intent.putExtra("applicationImageId", application.getImageId());
            intent.putExtra("applicationLatitude", application.getLatitude());
            intent.putExtra("applicationLongitude", application.getLongitude());

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
        TextView applicationId;
        TextView applicationName;
        TextView applicationDate;
        TextView applicationKpid;

        public ApplicationViewHolder(View itemView) {
            super(itemView);
            applicationId = itemView.findViewById(R.id.ApplicationId);
            applicationName = itemView.findViewById(R.id.ApplicationName);
            applicationDate = itemView.findViewById(R.id.ApplicationDate);
            applicationKpid = itemView.findViewById(R.id.ApplicationKpId);
        }
    }
}