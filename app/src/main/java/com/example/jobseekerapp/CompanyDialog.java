package com.example.jobseekerapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.jobseekerapp.API.ApiService;
import com.example.jobseekerapp.API.RetrofitClient;
import com.example.jobseekerapp.Models.Company;
import com.example.jobseekerapp.Models.CompanyResponse;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyDialog extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;

    public CompanyDialog() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_company_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_companies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadCompanies();

        return view;
    }

    private void loadCompanies() {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.getAllCompanies().enqueue(new Callback<CompanyResponse>() {
            @Override
            public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    List<Company> companyList = response.body().getData();
                    recyclerView.setAdapter(new CompanyAdapter(companyList, company -> {
                        Toast.makeText(getContext(), "اخترت: " + company.getName(), Toast.LENGTH_SHORT).show();
                        dismiss();
                    }));
                } else {
                    Toast.makeText(getContext(), "فشل تحميل الشركات", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CompanyResponse> call, Throwable t) {
                Toast.makeText(getContext(), "خطأ بالشبكة: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
