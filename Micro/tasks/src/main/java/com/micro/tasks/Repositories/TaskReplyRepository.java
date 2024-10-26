package com.micro.tasks.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.micro.tasks.Entities.TaskReply;

public interface TaskReplyRepository extends MongoRepository<TaskReply, Integer> {}
