package com.example.jobseekerapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobseekerapp.API.ApiService;
import com.example.jobseekerapp.API.RetrofitClient;
import com.example.jobseekerapp.Models.GeneralResponse;
import com.example.jobseekerapp.Models.Job;
import com.example.jobseekerapp.Models.JobResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    JobAdapter adapter;
    ImageView iconSearch;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler_jobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        iconSearch = view.findViewById(R.id.icon_search);
        iconSearch.setOnClickListener(v -> showCompanyDialog());

        loadJobs();

        return view;
    }

    private void loadJobs() {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.getJobs().enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Job> jobList = response.body().getData();

                    adapter = new JobAdapter(jobList,
                            job -> openJobDetails(job.getId()),
                            job -> addToBookmarks(job.getId())
                    );

                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(getContext(), "فشل في جلب البيانات", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JobResponse> call, Throwable t) {
                Toast.makeText(getContext(), "خطأ بالشبكة: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openJobDetails(int jobId) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new JobDetailsFragment(jobId))
                .addToBackStack(null)
                .commit();
    }

    private void addToBookmarks(int jobId) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.addBookmark(jobId).enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), response.body().getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                } else {


                    Toast.makeText(getContext(),  "غير مصرح لك بالاضافه الى المفضله", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Toast.makeText(getContext(), "خطأ بالشبكة: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCompanyDialog() {
        CompanyDialog dialog = new CompanyDialog();
        dialog.show(requireActivity().getSupportFragmentManager(), "CompanyDialog");
    }
}
