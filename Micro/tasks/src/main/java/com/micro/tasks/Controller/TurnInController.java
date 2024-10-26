package com.micro.tasks.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.tasks.Entities.Task;
import com.micro.tasks.Entities.TurnIn;
import com.micro.tasks.Entities.User;
import com.micro.tasks.Repositories.TaskRepository;
import com.micro.tasks.Repositories.UserRepository;
import com.micro.tasks.Services.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.micro.tasks.Config.AutoIncrementUtil;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/turnins")
public class TurnInController {

    @Autowired
    private IProjectService turnInService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private AutoIncrementUtil autoIncrementUtil;

    @PostMapping
    public ResponseEntity<TurnIn> submitTurnIn(@RequestParam("file") MultipartFile file, @RequestParam("turnIn") String turnInJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TurnIn turnIn = objectMapper.readValue(turnInJson, TurnIn.class);
            if (!file.isEmpty()) {
                turnIn.setAttachmentFileName(file.getOriginalFilename());
                turnIn.setAttachmentData(file.getBytes());
            }
            Task task = taskRepository.findById(turnIn.getTask().getId()).orElse(null);
            User student = userRepository.findById(turnIn.getStudent().getId()).orElse(null);
            User supervisor = userRepository.findById(turnIn.getSupervisor().getId()).orElse(null);
            if (task == null || student == null || supervisor == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            turnIn.setTask(task);
            turnIn.setStudent(student);
            turnIn.setSupervisor(supervisor);
            turnIn.setStudent(student);
            int id = autoIncrementUtil.getNextSequence("votre_sequence");
            turnIn.setId(id);
            TurnIn submittedTurnIn = turnInService.submitTurnIn(turnIn);
            taskRepository.delete(task);
            return new ResponseEntity<>(submittedTurnIn, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<TurnIn>> getAllTurnIns() {
        List<TurnIn> turnIns = turnInService.getAllTurnIns();
        return new ResponseEntity<>(turnIns, HttpStatus.OK);
    }

    @GetMapping("/{turnInId}")
    public ResponseEntity<TurnIn> getTurnInById(@PathVariable("turnInId") int turnInId) {
        TurnIn turnIn = turnInService.getTurnInById(turnInId);
        if (turnIn != null) {
            return new ResponseEntity<>(turnIn, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<TurnIn>> getTurnInsByStudentId(@PathVariable("studentId") int studentId) {
        List<TurnIn> turnIns = turnInService.getTurnInsByStudentId(studentId);
        return new ResponseEntity<>(turnIns, HttpStatus.OK);
    }
}