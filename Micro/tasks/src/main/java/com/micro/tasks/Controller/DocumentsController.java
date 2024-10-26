package com.micro.tasks.Controller;

import com.micro.tasks.Services.IProjectService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.micro.tasks.Entities.Documents;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api/documents")
public class DocumentsController {

    @Autowired
    private IProjectService iProjectService;

    // Get all documents
    @GetMapping
    public ResponseEntity<List<Documents>> getAllDocuments() {
        List<Documents> documents = iProjectService.getAlldocuments();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    // Get documents by id
    @GetMapping("/{id}")
    public ResponseEntity<Documents> getDocumentsById(@PathVariable Integer id) {
        Optional<Documents> documents = iProjectService.getdocumentsById(id);
        if (documents.isPresent()) {
            return new ResponseEntity<>(documents.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create documents
    @PostMapping
    public ResponseEntity<Documents> createDocuments(@RequestBody Documents documents) {
        try {
            Documents newDocuments = iProjectService.createdocuments(documents);
            return new ResponseEntity<>(newDocuments, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update documents
    @PutMapping("/{id}")
    public ResponseEntity<Documents> updateDocuments(@PathVariable Integer id, @RequestBody Documents documents) {
        Optional<Documents> oldDocuments = iProjectService.getdocumentsById(id);
        if (oldDocuments.isPresent()) {
            documents.setIdDoc(id);
            Documents updatedDocuments = iProjectService.updatedocuments(documents);
            return new ResponseEntity<>(updatedDocuments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete documents
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocuments(@PathVariable Integer id) {
        iProjectService.deletedocuments(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
