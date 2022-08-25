package com.example.studentservice;

import com.example.studentservice.model.entity.StudentEntity;
import com.example.studentservice.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ImportAutoConfiguration(
        exclude = EmbeddedMongoAutoConfiguration.class)
@ContextConfiguration(
        classes = {StudentServiceApplication.class},
        initializers = TestContainersApplicationTest.Initializer.class)
class TestContainersApplicationTest {
    @Container
    private static final MongoDBContainer mongoDb = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    static {
        mongoDb.start();
    }

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StudentRepository studentRepository;

    @Test
    void fullFlowTest() throws Exception {
        studentRepository.save(StudentEntity.builder()
                .id("12")
                .name("Marian")
                .age(23)
                .build()
        );
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

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@NotNull ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    String.format("spring.data.mongodb.uri: %s", mongoDb.getReplicaSetUrl())
            ).applyTo(configurableApplicationContext);
        }
    }
}