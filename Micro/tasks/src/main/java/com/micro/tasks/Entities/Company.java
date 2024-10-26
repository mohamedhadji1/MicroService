package com.micro.tasks.Entities;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "companies")
public class Company {

    private int idEntr;
    private String nom;
    private String email;

    public int getIdEntr() {
        return idEntr;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public void setIdEntr(int idEntr) {
        this.idEntr = idEntr;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
