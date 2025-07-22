package com.example.jobseekerapp.API;

import com.example.jobseekerapp.Models.CompanyResponse;
import com.example.jobseekerapp.Models.FaqsResponse;
import com.example.jobseekerapp.Models.GeneralResponse;
import com.example.jobseekerapp.Models.JobDetailsResponse;
import com.example.jobseekerapp.Models.JobResponse;
import com.example.jobseekerapp.Models.PolicyResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("ar/api/job-seeker/all-jobs")
    Call<JobResponse> getJobs();

    @GET("ar/api/job-seeker/job-details/{id}")
    Call<JobDetailsResponse> getJobDetails(@Path("id") int jobId);
    @GET("ar/api/faqs")
    Call<FaqsResponse> getAllFaqs();
    @GET("ar/api/job-seeker/favorite-jobs")
    Call<JobResponse> getFavoriteJobs();
    @POST("ar/api/job-seeker/jobs/{job_id}/mark-favorite")
    Call<GeneralResponse> addBookmark(@Path("job_id") int jobId);
    @GET("ar/api/all-companies")
    Call<CompanyResponse> getAllCompanies();
    @GET("ar/api/policies")
    Call<PolicyResponse> getPolicy();

    @POST("ar/api/job-seeker/jobs/applied/{job_id}")
    Call<GeneralResponse> applyJob(@Path("job_id") int jobId);

}
