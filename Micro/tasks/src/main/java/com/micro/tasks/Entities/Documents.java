package com.micro.tasks.Entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Documents")
public class Documents {

    private int idDoc;
    private Type type;

    public int getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(int idDoc) {
        this.idDoc = idDoc;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
