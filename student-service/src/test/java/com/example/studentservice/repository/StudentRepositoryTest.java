package com.example.studentservice.repository;

import com.example.studentservice.model.entity.StudentEntity;
import com.example.studentservice.service.StudentService;
import com.example.studentservice.service.api.CourseApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ImportAutoConfiguration(
        exclude = EmbeddedMongoAutoConfiguration.class)
class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentDAO studentDAO;

    @Mock
    private CourseApiClient courseApiClient;

    @BeforeEach
    public void setup() {
        studentRepository.deleteAll();
        studentService = new StudentService(studentRepository, studentDAO, courseApiClient);
    }

    @Test
    void testCreateStudent() {
        final String id = UUID.randomUUID().toString();
        final StudentEntity expected = StudentEntity.builder()
                .id(id)
                .name("Test")
                .age(21)
                .build();
        final StudentEntity result = studentService.createStudent(expected);

        assertThat(result).isNotNull().isEqualTo(expected);

        studentRepository.deleteById(id);
    }

}