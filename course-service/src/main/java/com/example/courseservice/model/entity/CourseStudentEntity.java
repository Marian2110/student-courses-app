package com.example.courseservice.model.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "course_student")
public record CourseStudentEntity(
        @Id
        String id,
        String courseId,
        String studentId,
        Integer grade) {
}
