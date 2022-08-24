package com.example.studentservice.repository;

import com.example.studentservice.model.entity.StudentEntity;
import com.example.studentservice.model.filter.StudentFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.support.PageableExecutionUtils.getPage;

@Repository
@RequiredArgsConstructor
public class StudentDAO {
    private final MongoTemplate mongoTemplate;

    public Page<StudentEntity> getStudents(final StudentFilter filter, final Pageable pageable) {
        final List<StudentEntity> studentEntities = mongoTemplate.find(filter.getQuery(), StudentEntity.class);
        return getPage(
                studentEntities,
                pageable,
                () -> mongoTemplate.count(
                        filter.getQuery(),
                        StudentEntity.class
                )
        );

    }
}
