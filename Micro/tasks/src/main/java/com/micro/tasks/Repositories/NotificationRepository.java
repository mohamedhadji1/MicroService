package com.micro.tasks.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.micro.tasks.Entities.Notification;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, Integer> {
}