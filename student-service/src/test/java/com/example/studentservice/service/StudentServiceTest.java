package com.example.studentservice.service;

import com.example.studentservice.model.entity.StudentEntity;
import com.example.studentservice.model.filter.StudentFilter;
import com.example.studentservice.repository.StudentDAO;
import com.example.studentservice.repository.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentDAO studentDAO;

    @Test
    @DisplayName("getStudents() should return Page<StudentEntity>")
    void getStudents() {
        when(studentDAO.getStudents(any(StudentFilter.class), any())).thenReturn(Page.empty());

        final StudentFilter filter = StudentFilter.builder().build();
        final Page<StudentEntity> students = studentService.getStudents(filter, Pageable.unpaged());
        assertEquals(0, students.getNumberOfElements());
    }

    @Test
    @DisplayName("getStudent() should return Optional<StudentEntity>")
    void getStudent() {
        when(studentRepository.findById(any(String.class))).thenReturn(Optional.empty());

        final String id = "id";
        final Optional<StudentEntity> student = studentService.getStudent(id);
        assertFalse(student.isPresent());
    }

    @Test
    @DisplayName("createStudent() should return StudentEntity")
    void createStudent() {
        when(studentRepository.save(any(StudentEntity.class))).thenReturn(StudentEntity.builder().build());

        final StudentEntity toEntity = StudentEntity.builder().build();
        final StudentEntity student = studentService.createStudent(toEntity);
        assertNotNull(student);
    }
}