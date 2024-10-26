package com.micro.tasks.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.micro.tasks.Entities.MonitoringNote;
import com.micro.tasks.Entities.Status;

import java.util.List;

@Repository
public interface MonitoringNoteRepository extends MongoRepository<MonitoringNote,Integer> {
    @Query("{'status': ?0}")
    List<MonitoringNote> findByStatus(Status status);
}
