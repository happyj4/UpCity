package com.example.upcity;

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
    private OnItemClickListener onItemClickListener;

    public ApplicationAdapter(List<Application> applicationList, OnItemClickListener onItemClickListener) {
        this.applicationList = applicationList;
        this.onItemClickListener = onItemClickListener;
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
            appHolder.applicationKpid.setText(String.valueOf(application.getKpid()));
        }

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(application);
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

    public interface OnItemClickListener {
        void onItemClick(Application application);
    }
}