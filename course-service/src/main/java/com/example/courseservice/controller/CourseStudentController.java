package com.example.courseservice.controller;

import com.example.courseservice.model.entity.JoinedCourseStudentEntity;
import com.example.courseservice.service.CourseStudentService;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.CollectionResponse;
import org.example.model.dto.PageInfo;
import org.example.model.dto.StudentCourse;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("course-student")
@Validated
public class CourseStudentController {

    private final CourseStudentService courseStudentService;

    @GetMapping
    public CollectionResponse<StudentCourse> getStudentCourses(
            @RequestParam
            @NotBlank(message = "studentId should not be null")
            final String studentId,
            Pageable pageable) {
        final var studentCourses = courseStudentService.getJoinedCourseStudents(studentId, pageable);
        return CollectionResponse.<StudentCourse>builder()
                .content(studentCourses.stream()
                        .map(studentCourse -> StudentCourse.builder()
                                .discipline(getDiscipline(studentCourse))
                                .grade(studentCourse.grade())
                                .build())
                        .toList())
                .pageInfo(PageInfo.builder()
                        .currentPage(studentCourses.getNumber())
                        .pageSize(studentCourses.getSize())
                        .totalPages(studentCourses.getTotalPages())
                        .totalElements(studentCourses.getNumberOfElements())
                        .build())
                .build();
    }

    private static String getDiscipline(final JoinedCourseStudentEntity studentCourse) {
        return Optional.ofNullable(studentCourse.courses())
                .filter(courses -> !courses.isEmpty())
                .map(courses -> courses.get(0).discipline())
                .orElse(null);
    }

}
