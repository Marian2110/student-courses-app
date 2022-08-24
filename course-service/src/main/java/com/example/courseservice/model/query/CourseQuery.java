package com.example.courseservice.model.query;

import com.example.courseservice.model.entity.CourseStudentEntity;
import com.example.courseservice.model.filter.CourseFilter;
import com.example.courseservice.service.CourseStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CourseQuery {
    private final CourseStudentService courseStudentService;

    public Query getQuery(final CourseFilter filter) {
        return new Query(getCriteria(filter));
    }

    private Criteria getCriteria(final CourseFilter filter) {
        final Criteria criteria = new Criteria();

        Optional.ofNullable(filter.discipline()).ifPresent(d -> criteria.and("discipline").regex(d));
        Optional.ofNullable(filter.description()).ifPresent(d -> criteria.and("description").regex(d));
        Optional.ofNullable(filter.studentId()).ifPresent(id -> criteria.and("studentId").in(getCourseByStudentId(id)));
        return criteria;
    }

    private List<String> getCourseByStudentId(final String id) {
        return courseStudentService
                .getCourseStudents(id)
                .stream()
                .map(CourseStudentEntity::courseId)
                .toList();
    }
}
