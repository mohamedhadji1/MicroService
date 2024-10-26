package com.micro.tasks.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.micro.tasks.Entities.TurnIn;

import java.util.List;

@Repository
public interface TurnInRepository extends MongoRepository<TurnIn, Integer> {
    List<TurnIn> findByStudentId(int studentId);
}
