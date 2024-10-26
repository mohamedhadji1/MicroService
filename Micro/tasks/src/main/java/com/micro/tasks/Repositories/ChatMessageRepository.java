package com.micro.tasks.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.micro.tasks.Entities.ChatMessage;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, Integer> {
    List<ChatMessage> findBySender_IdAndRecipient_Id(int supervisorId, int studentId);
}
