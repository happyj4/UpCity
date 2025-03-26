package com.example.upcity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder> {

    private List<Application> applicationList;
    private OnItemClickListener onItemClickListener;

    public ApplicationAdapter(List<Application> applicationList, OnItemClickListener onItemClickListener) {
        this.applicationList = applicationList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ApplicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.application_card, parent, false);
        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ApplicationViewHolder holder, int position) {
        Application application = applicationList.get(position);
        holder.applicationId.setText(String.valueOf(application.getId()));
        holder.applicationName.setText(application.getName());
        holder.applicationDate.setText(application.getCreationDate());
        holder.applicationKpid.setText(String.valueOf(application.getKpid()));

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

    // ViewHolder для плашки
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
            applicationKpid = itemView.findViewById(R.id.ApplicationId);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Application application);
    }
}
