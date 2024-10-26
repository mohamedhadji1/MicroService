package com.micro.tasks.Controller;
import com.micro.tasks.Config.AutoIncrementUtil;
import com.micro.tasks.Entities.Task;
import com.micro.tasks.Entities.TaskDTO;
import com.micro.tasks.Entities.User;
import com.micro.tasks.Repositories.TaskRepository;
import com.micro.tasks.Repositories.UserRepository;
import com.micro.tasks.Services.IProjectImp;
import com.micro.tasks.Services.IProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin("*")

public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private AutoIncrementUtil autoIncrementUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IProjectService iProjectService;
    @Autowired
    private IProjectImp iProjectImp;
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestParam("file") MultipartFile file, @RequestParam("task") String taskJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Task task = objectMapper.readValue(taskJson, Task.class);

            if (!file.isEmpty()) {
                task.setAttachmentFileName(file.getOriginalFilename());
                task.setAttachmentData(file.getBytes());
            }
            User supervisor = userRepository.findById(task.getSupervisor().getId()).orElse(null);
            User student = userRepository.findById(task.getStudent().getId()).orElse(null);
            if (supervisor == null || student == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            task.setSupervisor(supervisor);
            task.setStudent(student);
            int id = autoIncrementUtil.getNextSequence("votre_sequence");
            task.setId(id);
            Task createdTask = taskRepository.save(task);
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String progress,
            @RequestParam(required = false) String duration,
            @RequestParam(required = false) String supervisorName,
            @RequestParam(required = false) String studentName) {

        List<Task> tasks;

        if (description != null && !description.isEmpty()) {
            tasks = taskRepository.findByTaskDescriptionContainingIgnoreCase(description);
        } else if (progress != null && !progress.isEmpty()) {
            tasks = taskRepository.findByProgressContainingIgnoreCase(progress);
        } else if (duration != null && !duration.isEmpty()) {
            tasks = taskRepository.findByDurationContainingIgnoreCase(duration);
        } else if (supervisorName != null && !supervisorName.isEmpty()) {
            tasks = taskRepository.findBySupervisorNameContainingIgnoreCase(supervisorName);
        } else if (studentName != null && !studentName.isEmpty()) {
            tasks = taskRepository.findByStudentNameContainingIgnoreCase(studentName);
        } else {
            tasks = taskRepository.findAll();
        } if (!tasks.isEmpty()) {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable("taskId") int taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable("taskId") int taskId, @RequestBody Task updatedTask) {
        Task existingTask = taskRepository.findById(taskId).orElse(null);
        if (existingTask != null) {
            updatedTask.setId(taskId);
            Task savedTask = taskRepository.save(updatedTask);
            return new ResponseEntity<>(savedTask, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") int taskId) {
        Task existingTask = taskRepository.findById(taskId).orElse(null);
        if (existingTask != null) {
            taskRepository.deleteById(taskId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{taskId}/attachment/download")
    public ResponseEntity<org.springframework.core.io.Resource> downloadTaskAttachment(@PathVariable int taskId) {
        org.springframework.core.io.Resource resource = iProjectService.downloadTaskAttachment(taskId);
        if (resource != null) {
            String filename = iProjectService.getAttachmentFilename(taskId);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
