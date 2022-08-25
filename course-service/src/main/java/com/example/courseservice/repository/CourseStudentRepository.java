package com.example.courseservice.repository;

import com.example.courseservice.model.entity.CourseStudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseStudentRepository extends MongoRepository<CourseStudentEntity, String> {

    List<CourseStudentEntity> findByStudentId(String courseId);

    Page<CourseStudentEntity> findByCourseId(String id, final Pageable pageable);
}
