package com.micro.tasks.Repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.micro.tasks.Entities.User;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    User findByEmail(String email);
}
