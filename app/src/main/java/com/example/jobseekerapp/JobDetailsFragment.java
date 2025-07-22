package com.example.jobseekerapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.jobseekerapp.API.ApiService;
import com.example.jobseekerapp.API.RetrofitClient;
import com.example.jobseekerapp.Models.BusinessMan;
import com.example.jobseekerapp.Models.Country;
import com.example.jobseekerapp.Models.GeneralResponse;
import com.example.jobseekerapp.Models.JobDetailsResponse;
import com.example.jobseekerapp.Models.Job;
import com.example.jobseekerapp.Models.Skill;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailsFragment extends Fragment {

    ImageView iconSave, iconShare, logoCompany, imgBack, imgCountryFlag, imgJobLogoSmall;
    Button btnApply;
    TextView txtCompanyName, txtJobTitle, txtDescription, txtCountryName, txtSalary, txtWorkField, txtExperience,txtJobTime;
    TextView txtNationality, txtCountryResidence, txtGender, txtJobType;
    LinearLayout layoutSkills;


    int jobId;
    private Job job;

    public JobDetailsFragment(int jobId) {
        this.jobId = jobId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_details, container, false);

        iconSave = view.findViewById(R.id.icon_save);
        btnApply = view.findViewById(R.id.btn_apply);
        iconShare = view.findViewById(R.id.icon_share);
        txtCompanyName = view.findViewById(R.id.txt_company_name);
        logoCompany = view.findViewById(R.id.logo_company);
        imgBack = view.findViewById(R.id.img_back);
        txtJobTitle = view.findViewById(R.id.txt_job_title);
        txtDescription = view.findViewById(R.id.txt_description);
        txtCountryName = view.findViewById(R.id.txt_country_name);
        imgCountryFlag = view.findViewById(R.id.img_country_flag);
        txtSalary = view.findViewById(R.id.txt_salary);
        txtWorkField = view.findViewById(R.id.txt_work_field);
        txtExperience = view.findViewById(R.id.txt_experience);
        txtNationality = view.findViewById(R.id.txt_nationality);
        txtCountryResidence = view.findViewById(R.id.txt_country_residence);
        txtGender = view.findViewById(R.id.txt_gender);
        txtJobType = view.findViewById(R.id.txt_job_type);
        layoutSkills = view.findViewById(R.id.layout_skills);
        imgJobLogoSmall = view.findViewById(R.id.img_job_logo_small);
        txtJobTime = view.findViewById(R.id.txt_job_time);


        imgBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        iconSave.setOnClickListener(v -> addToBookmarks(jobId));
        btnApply.setOnClickListener(v -> showBottomSheet(R.layout.bottom_sheet_action));
        iconShare.setOnClickListener(v -> showBottomSheet(R.layout.layout_share_sheet));

        txtCompanyName.setOnClickListener(v -> openCompanyDetails());
        logoCompany.setOnClickListener(v -> openCompanyDetails());

        loadJobDetails();

        return view;
    }

    private void loadJobDetails() {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.getJobDetails(jobId).enqueue(new Callback<JobDetailsResponse>() {
            @Override
            public void onResponse(Call<JobDetailsResponse> call, Response<JobDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    job = response.body().getData();
                    if (job != null) {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String json = gson.toJson(job);
                        Log.d("JobDetailsLog", json);
                        displayJobDetails(job);
                    }
                } else {
                    Toast.makeText(getContext(), "فشل جلب التفاصيل", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JobDetailsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "خطأ في الاتصال: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayJobDetails(Job job) {
        txtJobTitle.setText(job.getTitle());
        txtDescription.setText(job.getSummary());
        txtSalary.setText(job.getSalary() != null ? job.getSalary() : "غير محدد");
        txtExperience.setText(job.getWork_experience() + " سنوات");
        txtJobType.setText(job.getEmployment_type() != null ? job.getEmployment_type() : "غير محدد");
        txtJobTime.setText(job.getCreate_time() != null ? job.getCreate_time() : "غير محدد");


        if (job.getBusiness_man() != null) {
            txtCompanyName.setText(job.getBusiness_man().getBusiness_name());



            if (job.getBusiness_man().getImage_url() != null) {
                Glide.with(this)
                        .load(job.getBusiness_man().getImage_url())
                        .circleCrop()
                        .into(logoCompany);
            }
        }

        if (job.getCountry_of_employment() != null) {
            txtCountryName.setText(job.getCountry_of_employment().getName());
            GlideApp.with(this)
                    .load(job.getCountry_of_employment().getCountry_image())
                    .circleCrop()
                    .into(imgCountryFlag);
        }

        if (job.getWork_field() != null) {
            txtWorkField.setText(job.getWork_field().getName());
        } else {
            txtWorkField.setText("غير محدد");
        }

        txtNationality.setText(job.getNationality() != null ? job.getNationality() : "غير محدد");
        txtCountryResidence.setText(job.getCountry_residence() != null ? job.getCountry_residence() : "غير محدد");
        txtGender.setText(job.getGender() != null ? job.getGender() : "غير محدد");

        layoutSkills.removeAllViews();
        if (job.getSkills() != null && !job.getSkills().isEmpty()) {
            for (Skill skill : job.getSkills()) {
                TextView skillView = new TextView(getContext());
                skillView.setText(skill.getName());
                skillView.setPadding(16, 8, 16, 8);
                skillView.setBackgroundResource(R.drawable.skill_background);
                skillView.setTextColor(getResources().getColor(R.color.teal_700));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(8, 0, 8, 0);
                skillView.setLayoutParams(params);
                layoutSkills.addView(skillView);
            }
        }
    }

    private void addToBookmarks(int jobId) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.addBookmark(jobId).enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body().getMessage().getMessage();
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "فشل في العملية", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Toast.makeText(getContext(), "خطأ بالشبكة: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void applyToJob(int jobId) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.applyJob(jobId).enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body().getMessage().getMessage();
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "غير مصرح لك", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Toast.makeText(getContext(), "خطأ بالشبكة: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openCompanyDetails() {
        if (job == null || job.getBusiness_man() == null) {
            Toast.makeText(getContext(), "بيانات الشركة غير متوفرة", Toast.LENGTH_SHORT).show();
            return;
        }

        BusinessMan businessMan = job.getBusiness_man();
        Country country = job.getCountry_of_employment();

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, CompanyDetailsFragment.newInstance(businessMan, country))
                .addToBackStack(null)
                .commit();
    }

    private void showBottomSheet(int layoutRes) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View sheetView = LayoutInflater.from(getContext()).inflate(layoutRes, null);
        bottomSheetDialog.setContentView(sheetView);

        LinearLayout layoutSMS = sheetView.findViewById(R.id.layout_sms);
        LinearLayout layoutWhatsApp = sheetView.findViewById(R.id.layout_whatsapp);
        LinearLayout layoutCall = sheetView.findViewById(R.id.layout_call);

        layoutSMS.setOnClickListener(v -> {
            applyToJob(jobId);
            bottomSheetDialog.dismiss();
        });

        layoutWhatsApp.setOnClickListener(v -> {
            applyToJob(jobId);
            bottomSheetDialog.dismiss();
        });

        layoutCall.setOnClickListener(v -> {
            applyToJob(jobId);
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }
}
