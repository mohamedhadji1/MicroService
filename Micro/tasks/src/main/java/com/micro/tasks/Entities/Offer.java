package com.micro.tasks.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.Objects;

@Document(collection = "offer")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Offer {

    @Id
    @MongoId
    private int id;
    private Date dateStart;
    private Date dateEnd;
    private TypeInternship typeInternship;

    @DBRef
    @JsonIgnore
    private Company company;

    public static Offer Empty() {
        return Offer.builder().id(-1).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return id == offer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}