package org.example.springbootrecapproject.repository;

import org.example.springbootrecapproject.model.MyTask;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRepo extends MongoRepository<MyTask, String> {

}
