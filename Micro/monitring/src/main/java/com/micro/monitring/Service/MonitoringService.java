package com.micro.monitring.Service;


import com.micro.monitring.Entity.Monitoring;
import com.micro.monitring.Entity.TaskDTO;
import com.micro.monitring.Repositories.MonitoringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class MonitoringService {


    @Autowired
    private MonitoringRepository monitoringRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Monitoring startMonitoring(String taskId) {
        try {
            // Check if task is already being monitored
            if (monitoringRepository.existsByTaskId(taskId)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Task is already being monitored"
                );
            }

            // Fetch task details from Tasks service
            TaskDTO task = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8081/api/tasks/" + taskId)
                    .retrieve()
                    .bodyToMono(TaskDTO.class)
                    .block();

            if (task == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Task not found with ID: " + taskId
                );
            }

            Monitoring monitoring = new Monitoring();
            monitoring.setTaskId(taskId);
            monitoring.setTaskTitle(task.getTitle());
            monitoring.setMonitoringStartDate(new Date());
            monitoring.setReviewedBySupervisor(false);

            return monitoringRepository.save(monitoring);

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error starting monitoring for task: " + taskId + " - " + e.getMessage()
            );
        }
    }

    public List<Monitoring> getAllMonitoredTasks() {
        try {
            return monitoringRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error fetching monitored tasks: " + e.getMessage()
            );
        }
    }

    public boolean isTaskMonitored(String taskId) {
        try {
            return monitoringRepository.existsByTaskId(taskId);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error checking task monitoring status: " + e.getMessage()
            );
        }
    }

    // Additional useful methods
    public Monitoring updateMonitoringStatus(String taskId, boolean reviewed) {
        Monitoring monitoring = monitoringRepository.findByTaskId(taskId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Monitoring not found for task: " + taskId
                ));

        monitoring.setReviewedBySupervisor(reviewed);
        return monitoringRepository.save(monitoring);
    }

    public void stopMonitoring(String taskId) {
        Monitoring monitoring = monitoringRepository.findByTaskId(taskId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Monitoring not found for task: " + taskId
                ));

        monitoringRepository.delete(monitoring);
    }

    /*public Monitoring createMonitoring(String taskId) {
        Monitoring monitoring = new Monitoring();
        monitoring.setTaskId(taskId);
        monitoring.setReviewedBySupervisor(false);
        return monitoringRepository.save(monitoring);
    }*/


}