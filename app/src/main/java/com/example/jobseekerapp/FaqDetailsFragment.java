package com.example.jobseekerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jobseekerapp.Models.Faq;

public class FaqDetailsFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_DESCRIPTION = "description";

    public static FaqDetailsFragment newInstance(Faq faq) {
        FaqDetailsFragment fragment = new FaqDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, faq.getTitle());
        args.putString(ARG_DESCRIPTION, faq.getDescription());
        fragment.setArguments(args);
        return fragment;
    }

    public FaqDetailsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq_details, container, false);

        TextView txtQuestion = view.findViewById(R.id.txt_question);
        TextView txtAnswer = view.findViewById(R.id.txt_answer);

        if (getArguments() != null) {
            txtQuestion.setText(getArguments().getString(ARG_TITLE));
            txtAnswer.setText(getArguments().getString(ARG_DESCRIPTION));
        }

        return view;
    }
}
