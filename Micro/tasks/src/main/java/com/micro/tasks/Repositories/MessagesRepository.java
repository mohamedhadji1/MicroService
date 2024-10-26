package com.micro.tasks.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.micro.tasks.Entities.ChatMessage;

public interface MessagesRepository  extends MongoRepository<ChatMessage, Integer> {
}
