package com.example.studentservice.controller;

import com.example.studentservice.StudentServiceApplication;
import com.example.studentservice.model.entity.StudentEntity;
import com.example.studentservice.model.mapper.StudentMapper;
import com.example.studentservice.service.StudentService;
import org.example.model.dto.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {StudentServiceApplication.class,})
class StudentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    @MockBean
    StudentMapper studentMapper;

    @Test
    @DisplayName("GET /students/id")
    void getStudent() throws Exception {
        final StudentEntity student = StudentEntity.builder()
                .id("12")
                .name("test")
                .age(21)
                .build();

        when(studentService.getStudent(anyString())).thenReturn(Optional.of(student));
        when(studentMapper.toApi(any(StudentEntity.class))).thenReturn(Student.builder()
                .id(student.id())
                .name(student.name())
                .age(student.age())
                .build());

        mockMvc.perform(get("/students/" + student.id()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": "12",
                            "name": "test",
                            "age": 21
                        }
                        """
                ));

        verify(studentService, atLeastOnce()).getStudent(anyString());
    }
}