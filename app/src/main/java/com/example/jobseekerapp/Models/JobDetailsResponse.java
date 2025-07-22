package com.example.jobseekerapp.Models;

public class JobDetailsResponse {
    private boolean status;
    private Message message;
    private Job data;

    public boolean isStatus() {
        return status;
    }

    public Message getMessage() {
        return message;
    }

    public Job getData() {
        return data;
    }


}
