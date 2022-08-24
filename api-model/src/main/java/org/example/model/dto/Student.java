package org.example.model.dto;

import lombok.Builder;

@Builder
public record Student(
        String id,
        String name,
        Integer age) {
}
