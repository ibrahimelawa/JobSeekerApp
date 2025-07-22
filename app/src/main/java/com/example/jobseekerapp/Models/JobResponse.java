package com.example.jobseekerapp.Models;

import java.util.List;

public class JobResponse {
    private boolean status;
    private Message message;
    private List<Job> data;

    public boolean isStatus() {
        return status;
    }

    public Message getMessage() {
        return message;
    }

    public List<Job> getData() {
        return data;
    }


}
