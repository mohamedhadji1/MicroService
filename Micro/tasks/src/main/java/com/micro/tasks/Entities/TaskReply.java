package com.micro.tasks.Entities;


import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection ="taskreply")

public class TaskReply {

    @Id
    private int id;
    private String comments;
    private String attachmentFileName;
    private byte[] attachmentData;
    @DBRef
    private Task tasks;
}
