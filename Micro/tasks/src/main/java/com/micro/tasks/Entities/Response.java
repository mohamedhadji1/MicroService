package com.micro.tasks.Entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "responses")
public class Response {

    private int idRep;
    private String description;

    public int getIdRep() {
        return idRep;
    }

    public void setIdRep(int idRep) {
        this.idRep = idRep;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
