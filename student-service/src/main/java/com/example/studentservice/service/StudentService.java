package com.example.studentservice.service;

import com.example.studentservice.model.entity.StudentEntity;
import com.example.studentservice.model.filter.StudentFilter;
import com.example.studentservice.repository.StudentDAO;
import com.example.studentservice.repository.StudentRepository;
import com.example.studentservice.service.api.CourseApiClient;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.CollectionResponse;
import org.example.model.dto.StudentCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentDAO studentDAO;
    private final CourseApiClient courseApiClient;

    public Page<StudentEntity> getStudents(final StudentFilter filter, final Pageable pageable) {
        return studentDAO.getStudents(filter, pageable);
    }

    public Optional<StudentEntity> getStudent(final String id) {
        return studentRepository.findById(id);
    }

    public StudentEntity createStudent(final StudentEntity toEntity) {
        return studentRepository.save(toEntity);
    }

    public CollectionResponse<StudentCourse> getStudentCourses(final String studentId, final Pageable pageable) {
        return courseApiClient.getStudentCourses(studentId, pageable);
    }

    public void deleteStudent(final String id) {
        studentRepository.deleteById(id);
    }
}
