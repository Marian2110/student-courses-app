package com.example.studentservice.model.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "students")
public record StudentEntity(
        @Id
        String id,
        String name,
        Integer age) {
}
