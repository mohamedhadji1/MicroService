package com.micro.tasks.Entities;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "defences")
public class Defence {

    private int idDef;

    public int getIdDef() {
        return idDef;
    }

    public void setIdDef(int idDef) {
        this.idDef = idDef;
    }
}
