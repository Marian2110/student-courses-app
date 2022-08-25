package com.example.courseservice.controller;

import com.example.courseservice.exception.custom.ResourceNotFoundException;
import com.example.courseservice.model.entity.CourseEntity;
import com.example.courseservice.model.entity.CourseStudentEntity;
import com.example.courseservice.model.filter.CourseFilter;
import com.example.courseservice.model.mapper.CourseMapper;
import com.example.courseservice.service.CourseService;
import com.example.courseservice.service.api.StudentApiClient;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.CollectionResponse;
import org.example.model.dto.Course;
import org.example.model.dto.PageInfo;
import org.example.model.dto.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final StudentApiClient studentApiClient;

    @GetMapping
    public CollectionResponse<Course> getCourses(final CourseFilter filter, final Pageable pageable) {
        final Page<CourseEntity> courses = courseService.getCourses(filter, pageable);
        return CollectionResponse.<Course>builder()
                .content(courseMapper.toApi(courses.getContent()))
                .pageInfo(PageInfo.builder()
                        .currentPage(courses.getPageable().getPageNumber())
                        .pageSize(courses.getSize())
                        .totalElements(courses.getNumberOfElements())
                        .totalPages(courses.getTotalPages())
                        .build())
                .build();
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseMapper.toApi(courseService.createCourse(courseMapper.toEntity(course)));
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable String id) {
        return courseMapper.toApi(courseService
                .getCourse(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity(Course.class, id)));
    }

    @GetMapping("/{id}/students")
    public CollectionResponse<Student> getStudentsForCourse(@PathVariable String id, Pageable pageable) {
        final Page<CourseStudentEntity> courseStudents = courseService.getStudentsForCourse(id, pageable);
        return CollectionResponse.<Student>builder()
                .content(courseStudents.getContent().stream()
                        .map(courseStudent -> studentApiClient.getStudentById(courseStudent.studentId())
                                .orElseThrow(() -> ResourceNotFoundException.forEntity(Student.class, courseStudent.studentId())))
                        .toList())
                .pageInfo(PageInfo.builder()
                        .currentPage(courseStudents.getPageable().getPageNumber())
                        .pageSize(courseStudents.getSize())
                        .totalElements(courseStudents.getNumberOfElements())
                        .totalPages(courseStudents.getTotalPages())
                        .build())
                .build();
    }

    @PostMapping("/{courseId}/students")
    public CourseStudentEntity addStudent(@PathVariable String courseId, @RequestBody Student student) {
        final String validatedStudentId = studentApiClient.getStudentById(student.id())
                .map(Student::id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity(Student.class, student.id()));

        final String ValidatedCourseId = courseService.getCourse(courseId)
                .map(CourseEntity::id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity(Course.class, courseId));

        return courseService.addStudent(ValidatedCourseId, validatedStudentId);
    }
}
