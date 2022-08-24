package com.example.courseservice.repository;

import com.example.courseservice.model.entity.CourseEntity;
import com.example.courseservice.model.filter.CourseFilter;
import com.example.courseservice.model.query.CourseQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.support.PageableExecutionUtils.getPage;

@Repository
@RequiredArgsConstructor
public class CourseDao {
    private final MongoTemplate mongoTemplate;
    private final CourseQuery courseQuery;

    public Page<CourseEntity> getCourses(final CourseFilter filter, final Pageable pageable) {
        final List<CourseEntity> courseEntities = mongoTemplate.find(courseQuery.getQuery(filter), CourseEntity.class);
        return getPage(
                courseEntities,
                pageable,
                () -> mongoTemplate.count(
                        courseQuery.getQuery(filter),
                        CourseEntity.class
                )
        );
    }
}
