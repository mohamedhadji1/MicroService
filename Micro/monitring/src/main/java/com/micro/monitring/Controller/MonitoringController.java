package com.micro.monitring.Controller;

import com.micro.monitring.Entity.Monitoring;
import com.micro.monitring.Service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/monitoring")
@CrossOrigin(origins = "http://localhost:4200") // For Angular frontend
public class MonitoringController {

    @Autowired
    private MonitoringService monitoringService;

    // Start monitoring a task
    @PostMapping("/start")
    public ResponseEntity<?> startMonitoring(@RequestBody Map<String, String> request) {
        try {
            String taskId = request.get("taskId");
            if (taskId == null || taskId.trim().isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body("Task ID is required");
            }

            Monitoring monitoring = monitoringService.startMonitoring(taskId.trim());
            return ResponseEntity.ok(monitoring);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to start monitoring: " + e.getMessage());
        }
    }

    // Get all monitored tasks
    @GetMapping
    public ResponseEntity<?> getAllMonitoredTasks() {
        try {
            List<Monitoring> tasks = monitoringService.getAllMonitoredTasks();
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch monitored tasks: " + e.getMessage());
        }
    }

    // Check if a task is being monitored
    @GetMapping("/check/{taskId}")
    public ResponseEntity<?> isTaskMonitored(@PathVariable String taskId) {
        try {
            boolean isMonitored = monitoringService.isTaskMonitored(taskId);
            return ResponseEntity.ok(isMonitored);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to check monitoring status: " + e.getMessage());
        }
    }

    // Update monitoring status (reviewed by supervisor)
    @PutMapping("/{taskId}/review")
    public ResponseEntity<?> updateReviewStatus(
            @PathVariable String taskId,
            @RequestBody Map<String, Boolean> request) {
        try {
            Boolean reviewed = request.get("reviewed");
            if (reviewed == null) {
                return ResponseEntity
                        .badRequest()
                        .body("Review status is required");
            }

            Monitoring monitoring = monitoringService.updateMonitoringStatus(taskId, reviewed);
            return ResponseEntity.ok(monitoring);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update review status: " + e.getMessage());
        }
    }

    // Stop monitoring a task
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> stopMonitoring(@PathVariable String taskId) {
        try {
            monitoringService.stopMonitoring(taskId);
            return ResponseEntity.ok()
                    .body(Map.of("message", "Monitoring stopped for task: " + taskId));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to stop monitoring: " + e.getMessage());
        }
    }

    // Get monitoring statistics
    @GetMapping("/stats")
    public ResponseEntity<?> getMonitoringStats() {
        try {
            Map<String, Object> stats = Map.of(
                    "totalMonitored", monitoringService.getAllMonitoredTasks().size(),
                    "reviewed", monitoringService.getAllMonitoredTasks()
                            .stream()
                            .filter(Monitoring::isReviewedBySupervisor)
                            .count()
            );
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch monitoring stats: " + e.getMessage());
        }
    }

    // Handle validation errors
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleValidationErrors(IllegalArgumentException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }
}