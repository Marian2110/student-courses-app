package com.example.studentservice.controller;

import com.example.studentservice.exception.custom.ResourceNotFoundException;
import com.example.studentservice.model.entity.StudentEntity;
import com.example.studentservice.model.filter.StudentFilter;
import com.example.studentservice.model.mapper.StudentMapper;
import com.example.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.CollectionResponse;
import org.example.model.dto.PageInfo;
import org.example.model.dto.Student;
import org.example.model.dto.StudentCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @GetMapping
    public CollectionResponse<Student> getStudents(StudentFilter filter, Pageable pageable) {
        Page<StudentEntity> students = studentService.getStudents(filter, pageable);
        return CollectionResponse.<Student>builder()
                .content(studentMapper.toApi(students.getContent()))
                .pageInfo(PageInfo.builder()
                        .currentPage(students.getPageable().getPageNumber())
                        .pageSize(students.getSize())
                        .totalElements(students.getNumberOfElements())
                        .totalPages(students.getTotalPages())
                        .build())
                .build();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable String id) {
        return studentMapper.toApi(studentService
                .getStudent(id)
                .orElseThrow(() -> getResourceNotFoundException(id)));
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentMapper.toApi(studentService.createStudent(studentMapper.toEntity(student)));
    }

    @GetMapping("/{studentId}/courses")
    public CollectionResponse<StudentCourse> getStudentCoursesInfo(@PathVariable String studentId, Pageable pageable) {
        final StudentEntity studentEntity = studentService
                .getStudent(studentId)
                .orElseThrow(() -> getResourceNotFoundException(studentId));

        final var studentCourses = studentService.getStudentCourses(studentId, pageable);

        final List<StudentCourse> content = studentCourses.content().stream()
                .map(studentCourse -> StudentCourse.builder()
                        .name(studentEntity.name())
                        .age(studentEntity.age())
                        .discipline(studentCourse.discipline())
                        .grade(studentCourse.grade())
                        .build())
                .toList();

        return CollectionResponse.<StudentCourse>builder()
                .content(content)
                .pageInfo(studentCourses.pageInfo())
                .build();
    }

    private ResourceNotFoundException getResourceNotFoundException(String id) {
        return ResourceNotFoundException.forEntity(Student.class, id);
    }
}
