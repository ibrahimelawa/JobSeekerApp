package com.example.jobseekerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobseekerapp.API.ApiService;
import com.example.jobseekerapp.API.RetrofitClient;
import com.example.jobseekerapp.Models.Job;
import com.example.jobseekerapp.Models.JobResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkFragment extends Fragment {

    private RecyclerView recyclerView;

    public BookmarkFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);

        recyclerView = view.findViewById(R.id.recycler_bookmarks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadBookmarks();

        return view;
    }

    private void loadBookmarks() {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.getFavoriteJobs().enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Job> bookmarks = response.body().getData();

                    JobAdapter adapter = new JobAdapter(bookmarks,
                            job -> openJobDetails(job.getId()),
                            job -> Toast.makeText(getContext(), "تمت الإضافة إلى المفضلة", Toast.LENGTH_SHORT).show()
                    );

                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(getContext(), "فشل تحميل المفضلة", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JobResponse> call, Throwable t) {
                Toast.makeText(getContext(), "خطأ في الاتصال: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openJobDetails(int jobId) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new JobDetailsFragment(jobId))
                .addToBackStack(null)
                .commit();
    }
}
