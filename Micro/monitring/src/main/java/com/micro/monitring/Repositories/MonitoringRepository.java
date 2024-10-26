package com.micro.monitring.Repositories;

import com.micro.monitring.Entity.Monitoring;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonitoringRepository extends JpaRepository<Monitoring, Long> {
    boolean existsByTaskId(String taskId);
    Optional<Monitoring> findByTaskId(String taskId);

}