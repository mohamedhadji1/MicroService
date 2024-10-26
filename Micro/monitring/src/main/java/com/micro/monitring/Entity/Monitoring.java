package com.micro.monitring.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "monitoring")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Monitoring {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String taskId; // MongoDB Task ID

    private boolean reviewedBySupervisor;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date monitoringStartDate;

    private String taskTitle; // To store task title for quick reference
}
