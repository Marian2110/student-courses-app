package com.example.courseservice.model.entity;

import org.example.model.dto.Course;

import java.util.List;

public record JoinedCourseStudentEntity(
        String _id,
        String courseId,
        String studentId,
        Integer grade,
        List<Course> courses) {
}
