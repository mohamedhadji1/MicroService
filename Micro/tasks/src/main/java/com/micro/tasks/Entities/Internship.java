package com.micro.tasks.Entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "internships")
public class Internship {

    private int id;
    private int idCompany;
    private String duration;
    private String subject;
    private String description;
    private String degreeStageO;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDegreeStageO() {
        return degreeStageO;
    }

    public void setDegreeStageO(String degreeStageO) {
        this.degreeStageO = degreeStageO;
    }
}
