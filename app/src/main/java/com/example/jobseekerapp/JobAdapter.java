package com.example.jobseekerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jobseekerapp.Models.Job;
import com.example.jobseekerapp.Models.Skill;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private List<Job> jobList;
    private OnJobClickListener listener;
    private OnJobBookmarkListener bookmarkListener;

    public interface OnJobClickListener {
        void onJobClick(Job job);
    }

    public interface OnJobBookmarkListener {
        void onBookmarkClick(Job job);
    }

    public JobAdapter(List<Job> jobList, OnJobClickListener listener, OnJobBookmarkListener bookmarkListener) {
        this.jobList = jobList;
        this.listener = listener;
        this.bookmarkListener = bookmarkListener;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = jobList.get(position);

        holder.txtJobTitle.setText(job.getTitle() != null ? job.getTitle() : "Job Title");
        holder.txtTime.setText(job.getCreate_time() != null ? job.getCreate_time() : "غير محدد");

        if (job.getBusiness_man() != null) {
            holder.txtCompanyName.setText(job.getBusiness_man().getBusiness_name() != null ? job.getBusiness_man().getBusiness_name() : "Unknown Company");

            String imageUrl = job.getBusiness_man().getImage_url();
            if (imageUrl != null) {
                Glide.with(holder.itemView.getContext()).load(imageUrl).circleCrop().into(holder.imgLogoCompany);
            } else {
                setDefaultImages(holder);
            }
        } else {
            holder.txtCompanyName.setText("Unknown Company");
            setDefaultImages(holder);
        }

        holder.txtField.setText(job.getWork_field() != null ? job.getWork_field().getName() : "N/A");
        holder.txtSalary.setText(job.getSalary() != null ? job.getSalary() : "N/A");
        holder.txtExperience.setText(job.getExperience_year() != null ? job.getExperience_year().getName() : "N/A");
        holder.txtExpiry.setText(job.getJob_valid_unite() != null ? job.getJob_valid_unite() : "N/A");
        holder.txtDescription.setText(job.getSummary() != null ? job.getSummary() : "N/A");

        holder.layoutSkills.removeAllViews();
        if (job.getSkills() != null && !job.getSkills().isEmpty()) {
            for (Skill skill : job.getSkills()) {
                addSkillView(holder, skill.getName());
            }
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onJobClick(job);
        });

        holder.imgBookmark.setOnClickListener(v -> {
            if (bookmarkListener != null) bookmarkListener.onBookmarkClick(job);
        });

        holder.imgShare.setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(holder.itemView.getContext());
            View sheet = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.layout_share_sheet, null);
            dialog.setContentView(sheet);
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    private void setDefaultImages(JobViewHolder holder) {
        holder.imgLogoCompany.setImageResource(R.drawable.ic_language);
        holder.imgJobLogoSmall.setImageResource(R.drawable.ic_language);
    }

    private void addSkillView(JobViewHolder holder, String skillName) {
        TextView skillView = new TextView(holder.itemView.getContext());
        skillView.setText(skillName);
        skillView.setBackgroundResource(R.drawable.skill_background);
        skillView.setPadding(16, 8, 16, 8);
        skillView.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.teal_700));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 0, 8, 0);

        skillView.setLayoutParams(params);
        holder.layoutSkills.addView(skillView);
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {

        TextView txtTime, txtJobTitle, txtCompanyName, txtField, txtSalary, txtExperience, txtExpiry, txtDescription;
        LinearLayout layoutSkills;
        ImageView imgShare, imgBookmark, imgLogoCompany, imgJobLogoSmall;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTime = itemView.findViewById(R.id.txt_time);
            txtJobTitle = itemView.findViewById(R.id.txt_job_title);
            txtCompanyName = itemView.findViewById(R.id.txt_company_name);
            txtField = itemView.findViewById(R.id.txt_field);
            txtSalary = itemView.findViewById(R.id.txt_salary);
            txtExperience = itemView.findViewById(R.id.txt_experience);
            txtExpiry = itemView.findViewById(R.id.txt_expiry);
            txtDescription = itemView.findViewById(R.id.txt_description);
            layoutSkills = itemView.findViewById(R.id.layout_skills);

            imgShare = itemView.findViewById(R.id.img_share);
            imgBookmark = itemView.findViewById(R.id.img_bookmark);
            imgLogoCompany = itemView.findViewById(R.id.logo_company);
            imgJobLogoSmall = itemView.findViewById(R.id.img_job_logo_small);
        }
    }
}
