package com.example.courseservice.model.entity;

import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@With
@Builder
@Document(collection = "courses")
public record CourseEntity(
        @Id
        String id,
        String discipline,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
