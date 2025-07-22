package com.example.jobseekerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobseekerapp.API.ApiService;
import com.example.jobseekerapp.API.RetrofitClient;
import com.example.jobseekerapp.Models.Faq;
import com.example.jobseekerapp.Models.FaqsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqsFragment extends Fragment {

    ImageView img_back;

    public FaqsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faqs, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_faqs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        img_back = view.findViewById(R.id.img_back);
        img_back.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        getFaqs(recyclerView);

        return view;
    }

    private void getFaqs(RecyclerView recyclerView) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.getAllFaqs().enqueue(new Callback<FaqsResponse>() {
            @Override
            public void onResponse(Call<FaqsResponse> call, Response<FaqsResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    List<Faq> faqList = response.body().getData();
                    FaqAdapter adapter = new FaqAdapter(faqList, faq -> {
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, FaqDetailsFragment.newInstance(faq))
                                .addToBackStack(null)
                                .commit();
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "فشل جلب البيانات", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FaqsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "فشل الاتصال", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
