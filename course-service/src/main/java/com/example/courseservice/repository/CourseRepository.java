package com.example.courseservice.repository;

import com.example.courseservice.model.entity.CourseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<CourseEntity, String> {
}
