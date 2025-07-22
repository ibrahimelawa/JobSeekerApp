package com.example.jobseekerapp.Models;

import java.util.List;

public class Job {

    private int id;
    private String title;
    private String employment_type;

    private String work_place;
    private Country country_of_employment;
    private String salary;
    private int salary_show;
    private int work_experience;
    private String job_valid_unite;
    private String summary;
    private boolean is_favorite;
    private boolean is_applied;
    private EducationLevel education_level;
    private EducationFeild education_feild;
    private Certification certification;
    private ExperienceYear experience_year;
    private BusinessMan business_man;
    private WorkField work_field;
    private List<Skill> skills;
    private String nationality;
    private String country_residence;
    private String gender;
    private String job_type;
    private String create_time;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getEmployment_type() {
        return employment_type;
    }

    public String getWork_place() {
        return work_place;
    }

    public Country getCountry_of_employment() {
        return country_of_employment;
    }

    public String getSalary() {
        return salary;
    }

    public int getSalary_show() {
        return salary_show;
    }

    public int getWork_experience() {
        return work_experience;
    }

    public String getJob_valid_unite() {
        return job_valid_unite;
    }

    public String getSummary() {
        return summary;
    }

    public boolean isFavorite() {
        return is_favorite;
    }

    public boolean isApplied() {
        return is_applied;
    }

    public EducationLevel getEducation_level() {
        return education_level;
    }

    public EducationFeild getEducation_feild() {
        return education_feild;
    }

    public Certification getCertification() {
        return certification;
    }

    public ExperienceYear getExperience_year() {
        return experience_year;
    }

    public BusinessMan getBusiness_man() {
        return business_man;
    }

    public WorkField getWork_field() {
        return work_field;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public String getNationality() {
        return nationality;
    }

    public String getCountry_residence() {
        return country_residence;
    }

    public String getGender() {
        return gender;
    }

    public String getJob_type() {
        return job_type;
    }
    public String getCreate_time() {
        return create_time;
    }

}
