package com.example.studentservice.repository;

import com.example.studentservice.model.entity.StudentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=5.0.2")
class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    void testCreateStudent() {
        final String id = UUID.randomUUID().toString();
        final StudentEntity expected = StudentEntity.builder()
                .id(id)
                .name("Test")
                .age(21)
                .build();
        final StudentEntity result = studentRepository.save(expected);

        assertThat(result).isNotNull().isEqualTo(expected);

        studentRepository.deleteById(id);
    }
}