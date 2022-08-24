package com.example.studentservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
//@ContextConfiguration(classes = {StudentServiceApplication.class, TestConfig.class})
class StudentServiceApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertTrue(true);
    }

}
