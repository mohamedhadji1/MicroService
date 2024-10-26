package com.micro.tasks.Controller;

import com.micro.tasks.Entities.MonitoringNote;
import com.micro.tasks.Entities.Status;
import com.micro.tasks.Entities.TurnIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.micro.tasks.Config.AutoIncrementUtil;
import com.micro.tasks.Repositories.TurnInRepository;
import com.micro.tasks.Services.IProjectImp;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/monitoring")
@CrossOrigin(origins = "http://localhost:4200")
public class MonitoringNoteController {

    @Autowired
    private IProjectImp monitoringNoteService;
    @Autowired
    private TurnInRepository turnInRepository;
    @Autowired
    private AutoIncrementUtil autoIncrementUtil;

    @PostMapping
    public ResponseEntity<MonitoringNote> createMonitoringNote(@RequestBody MonitoringNote monitoringNote) {
        try {
            TurnIn turnIn = turnInRepository.findById(monitoringNote.getTurnIn().getId()).orElse(null);
            if (turnIn == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            monitoringNote.setTurnIn(turnIn);
            int id = autoIncrementUtil.getNextSequence("votre_sequence");
            monitoringNote.setId(id);
            MonitoringNote createdNote = monitoringNoteService.createMonitoringNote(monitoringNote);
            return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<MonitoringNote>> getAllMonitoringNotes() {
        List<MonitoringNote> monitoringNotes = monitoringNoteService.getAllMonitoringNotes();
        return new ResponseEntity<>(monitoringNotes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonitoringNote> getMonitoringNoteById(@PathVariable("id") int id) {
        Optional<MonitoringNote> monitoringNote = monitoringNoteService.getMonitoringNoteById(id);
        return monitoringNote.map(note -> new ResponseEntity<>(note, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonitoringNote> updateMonitoringNote(@PathVariable("id") int id,
                                                               @RequestBody MonitoringNote monitoringNote) {
        monitoringNote.setId(id);
        MonitoringNote updatedNote = monitoringNoteService.updateMonitoringNote(monitoringNote);
        return new ResponseEntity<>(updatedNote, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonitoringNoteById(@PathVariable("id") int id) {
        monitoringNoteService.deleteMonitoringNoteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/notes")
    public List<MonitoringNote> getMonitoringNotesByStatus(@RequestParam("status") Status status) {
        return monitoringNoteService.getMonitoringNotesByStatus(status);
    }
}
