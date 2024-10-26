package com.micro.tasks.Entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "monitoring_notes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MonitoringNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date submissionDate = new Date();
    private String comment;
    private double grade;
    private Status status;
    @DBRef
    private TurnIn turnIn;
}
