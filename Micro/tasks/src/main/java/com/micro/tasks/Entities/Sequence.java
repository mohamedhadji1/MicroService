package com.micro.tasks.Entities;

import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "sequences")
public class Sequence {
    @Id
    private String id;
    private int seq;
}