package com.micro.tasks.Repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.micro.tasks.Entities.Monitoring;

@Repository
public interface TaskMonitoringRepository extends MongoRepository<Monitoring, Integer> {}
