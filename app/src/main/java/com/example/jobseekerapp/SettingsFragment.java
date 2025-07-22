package com.example.jobseekerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class SettingsFragment extends Fragment {

    LinearLayout optionJobPreference, optionFaq, optionHelp, optionPrivacy, optionLanguage, optionNotification;

    public SettingsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        optionJobPreference = view.findViewById(R.id.option_job_preference);
        optionFaq = view.findViewById(R.id.option_faq);
        optionHelp = view.findViewById(R.id.option_help);
        optionPrivacy = view.findViewById(R.id.option_privacy);
        optionLanguage = view.findViewById(R.id.option_language);
        optionNotification = view.findViewById(R.id.option_notification);

        optionPrivacy.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PrivacyPolicyFragment())
                    .addToBackStack(null)
                    .commit();
        });

        optionFaq.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FaqsFragment())
                    .addToBackStack(null)
                    .commit();
        });

        optionHelp.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HelpFragment())
                    .addToBackStack(null)
                    .commit();
        });

        optionLanguage.setOnClickListener(v -> showLanguageBottomSheet());

        optionNotification.setOnClickListener(v -> showNotificationBottomSheet());

        return view;
    }

    private void showLanguageBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View sheetView = LayoutInflater.from(getContext()).inflate(R.layout.layout_language, null);
        bottomSheetDialog.setContentView(sheetView);

        LinearLayout optionArabic = sheetView.findViewById(R.id.option_arabic);
        LinearLayout optionEnglish = sheetView.findViewById(R.id.option_english);
        ImageView checkArabic = sheetView.findViewById(R.id.check_arabic);
        ImageView checkEnglish = sheetView.findViewById(R.id.check_english);

        checkArabic.setVisibility(View.VISIBLE);
        checkEnglish.setVisibility(View.GONE);

        optionArabic.setOnClickListener(v -> {
            checkArabic.setVisibility(View.VISIBLE);
            checkEnglish.setVisibility(View.GONE);
            Toast.makeText(getContext(), "العربية مختارة", Toast.LENGTH_SHORT).show();
        });

        optionEnglish.setOnClickListener(v -> {
            checkArabic.setVisibility(View.GONE);
            checkEnglish.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "English Selected", Toast.LENGTH_SHORT).show();
        });

        bottomSheetDialog.show();
    }

    private void showNotificationBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View sheetView = LayoutInflater.from(getContext()).inflate(R.layout.layout_notification_sheet, null);
        bottomSheetDialog.setContentView(sheetView);

        Switch notificationSwitch = sheetView.findViewById(R.id.switch_notifications);

        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(getContext(), "Notifications Enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Notifications Disabled", Toast.LENGTH_SHORT).show();
            }
        });

        bottomSheetDialog.show();
    }
}
