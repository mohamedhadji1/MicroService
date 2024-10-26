package com.micro.tasks.Entities;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection ="TurnIn")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnIn {

    @Id
    private int id;
    private Date submissionDate = new Date();
    private String comment;
    private String attachmentFileName;
    private byte[] attachmentData;
    @DBRef
    private User student;
    @DBRef
    private User supervisor;
    @DBRef
    private Task task;

}
