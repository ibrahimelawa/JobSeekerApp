package com.example.jobseekerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jobseekerapp.API.ApiService;
import com.example.jobseekerapp.API.RetrofitClient;
import com.example.jobseekerapp.Models.PolicyResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPolicyFragment extends Fragment {

    TextView txtTitle, txtContent;

    public PrivacyPolicyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_privacy_policy, container, false);

        txtTitle = view.findViewById(R.id.txt_title);
        txtContent = view.findViewById(R.id.txt_content);

        loadPrivacyPolicy();

        return view;
    }

    private void loadPrivacyPolicy() {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.getPolicy().enqueue(new Callback<PolicyResponse>() {
            @Override
            public void onResponse(Call<PolicyResponse> call, Response<PolicyResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    txtTitle.setText(response.body().getData().getTitle());
                    txtContent.setText(response.body().getData().getDescription());
                } else {
                    Toast.makeText(getContext(), "فشل تحميل سياسة الخصوصية", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PolicyResponse> call, Throwable t) {
                Toast.makeText(getContext(), "خطأ بالشبكة: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
