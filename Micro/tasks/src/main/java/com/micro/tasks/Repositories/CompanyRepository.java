package com.micro.tasks.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.micro.tasks.Entities.Company;

@Repository
public interface CompanyRepository extends MongoRepository<Company, Integer> {}

