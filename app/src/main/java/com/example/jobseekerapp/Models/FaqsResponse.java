package com.example.jobseekerapp.Models;

import java.util.List;

public class FaqsResponse {
    private boolean status;
    private List<Faq> data;

    public boolean isStatus() {

        return status;
    }

    public List<Faq> getData() {

        return data;
    }
}
