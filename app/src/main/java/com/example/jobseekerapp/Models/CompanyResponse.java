package com.example.jobseekerapp.Models;

import java.util.List;

public class CompanyResponse {
    private boolean status;
    private Message message;
    private List<Company> data;

    public boolean isStatus() {
        return status;
    }

    public Message getMessage() {
        return message;
    }

    public List<Company> getData() {
        return data;
    }


}
