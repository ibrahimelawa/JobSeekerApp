package com.example.jobseekerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.jobseekerapp.Models.BusinessMan;
import com.example.jobseekerapp.Models.Country;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CompanyDetailsFragment extends Fragment {

    private static final String ARG_COMPANY_NAME = "company_name";
    private static final String ARG_COMPANY_BIO = "company_bio";
    private static final String ARG_COMPANY_TYPE = "company_type";
    private static final String ARG_EMPLOYEES = "company_employees";
    private static final String ARG_COUNTRY_NAME = "company_country";
    private static final String ARG_COUNTRY_IMAGE = "company_country_image";
    private static final String ARG_IMAGE_URL = "company_image";
    private static final String ARG_COVER_URL = "company_cover";

    private String companyName, companyBio, companyEmployees, countryName, countryImage, imageUrl, coverUrl, companyType;

    ImageView imgBack, imgCompanyLogo, imgCountryFlag, imgCover;
    TextView txtCompanyName, txtCompanyType, txtEmployees, txtCountry, txtBio, txtReadMore;

    public CompanyDetailsFragment() {
    }

    public static CompanyDetailsFragment newInstance(BusinessMan businessMan, Country country) {
        CompanyDetailsFragment fragment = new CompanyDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COMPANY_NAME, businessMan.getBusiness_name());
        args.putString(ARG_COMPANY_BIO, businessMan.getBio());
        args.putString(ARG_COMPANY_TYPE, String.valueOf(businessMan.getType_business()));
        args.putString(ARG_EMPLOYEES, businessMan.getEmployee_no());
        args.putString(ARG_IMAGE_URL, businessMan.getImage_url());
        args.putString(ARG_COVER_URL, businessMan.getCover_url());

        if (country != null) {
            args.putString(ARG_COUNTRY_NAME, country.getName());
            args.putString(ARG_COUNTRY_IMAGE, country.getCountry_image());
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            companyName = getArguments().getString(ARG_COMPANY_NAME);
            companyBio = getArguments().getString(ARG_COMPANY_BIO);
            companyType = getArguments().getString(ARG_COMPANY_TYPE);
            companyEmployees = getArguments().getString(ARG_EMPLOYEES);
            countryName = getArguments().getString(ARG_COUNTRY_NAME);
            countryImage = getArguments().getString(ARG_COUNTRY_IMAGE);
            imageUrl = getArguments().getString(ARG_IMAGE_URL);
            coverUrl = getArguments().getString(ARG_COVER_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_details, container, false);

        imgBack = view.findViewById(R.id.img_back);
        imgCompanyLogo = view.findViewById(R.id.img_company_logo);
        imgCountryFlag = view.findViewById(R.id.img_country_flag);
        imgCover = view.findViewById(R.id.img_cover);

        txtCompanyName = view.findViewById(R.id.txt_company_name);
        txtCompanyType = view.findViewById(R.id.txt_company_type);
        txtEmployees = view.findViewById(R.id.txt_employees);
        txtCountry = view.findViewById(R.id.txt_country);
        txtBio = view.findViewById(R.id.txt_bio);
        txtReadMore = view.findViewById(R.id.read_more);

        txtCompanyName.setText(companyName != null ? companyName : "N/A");
        txtCompanyType.setText(companyType != null ? companyType : "N/A");
        txtEmployees.setText(companyEmployees != null ? companyEmployees : "N/A");
        txtCountry.setText(countryName != null ? countryName : "N/A");
        txtBio.setText(companyBio != null ? companyBio : "N/A");

        if (imageUrl != null) {
            Glide.with(this).load(imageUrl).circleCrop().into(imgCompanyLogo);
        }

        if (countryImage != null) {
            Glide.with(this).load(countryImage).circleCrop().into(imgCountryFlag);
        }

        if (coverUrl != null) {
            Glide.with(this).load(coverUrl).centerCrop().into(imgCover);
        }

        imgBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        txtReadMore.setOnClickListener(v -> showBioBottomSheet());

        return view;
    }

    private void showBioBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View sheetView = LayoutInflater.from(getContext()).inflate(R.layout.layout_bio_sheet, null);
        TextView txtBioContent = sheetView.findViewById(R.id.txt_bio_content);
        txtBioContent.setText(companyBio != null ? companyBio : "N/A");

        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }
}
