package com.example.studentservice;

import com.example.studentservice.model.entity.StudentEntity;
import com.example.studentservice.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataMongo
@TestPropertySource(properties = "spring.mongodb.embedded.version=5.0.2")
class InMemoryApplicationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    StudentRepository studentRepository;

    @BeforeEach
    void setup() {
        studentRepository.save(StudentEntity.builder()
                .id("12")
                .name("Marian")
                .age(23)
                .build()
        );
    }

    @Test
    void fullFlowTest() throws Exception {
        mockMvc.perform(get("/students?name=Marian&page0&size=2"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            {
                                "content":[
                                    {
                                      "id": "12",
                                      "name": "Marian",
                                      "age": 23
                                    }
                                ],
                                "pageInfo":{
                                    "currentPage":0,
                                    "pageSize":2,
                                    "totalPages":1,
                                    "totalElements":1
                                }
                            }
                        """
                ));
    }
}