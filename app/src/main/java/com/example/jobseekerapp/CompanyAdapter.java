package com.example.jobseekerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobseekerapp.Models.Company;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {

    List<Company> companyList;
    OnCompanyClickListener listener;

    public interface OnCompanyClickListener {
        void onCompanyClick(Company company);
    }

    public CompanyAdapter(List<Company> companyList, OnCompanyClickListener listener) {
        this.companyList = companyList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        Company company = companyList.get(position);
        holder.txtName.setText(company.getName());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCompanyClick(company);
            }
        });
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public static class CompanyViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;

        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(android.R.id.text1);
        }
    }
}
