package com.example.jobseekerapp.Models;



public class PolicyResponse {
    private boolean status;
    private Message message;
    private PolicyData data;

    public boolean isStatus() {
        return status;
    }

    public Message getMessage() {
        return message;
    }

    public PolicyData getData() {
        return data;
    }

    public class PolicyData {
        private String title;
        private String description;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}
