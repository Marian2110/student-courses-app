package com.example.studentservice.repository;

import com.example.studentservice.model.entity.StudentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<StudentEntity, String> {

}
