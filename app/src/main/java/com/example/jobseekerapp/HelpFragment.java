package com.example.jobseekerapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class HelpFragment extends Fragment {

    EditText editFullName, editEmail, editSubject, editDetails;
    Button btnSend;
    ImageView img_back;

    public HelpFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_help, container, false);

        editFullName = view.findViewById(R.id.edit_full_name);
        editEmail = view.findViewById(R.id.edit_email);
        editSubject = view.findViewById(R.id.edit_subject);
        editDetails = view.findViewById(R.id.edit_details);
        btnSend = view.findViewById(R.id.btn_send);
        img_back = view.findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
        btnSend.setOnClickListener(v -> sendFeedback());

        return view;
    }

    private void sendFeedback() {
        String name = editFullName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String subject = editSubject.getText().toString().trim();
        String details = editDetails.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || subject.isEmpty() || details.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Feedback Sent!", Toast.LENGTH_SHORT).show();

        }
    }
}
