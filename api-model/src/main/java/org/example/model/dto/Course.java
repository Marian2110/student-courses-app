package org.example.model.dto;

import lombok.Builder;

@Builder
public record Course(
        String id,
        String discipline,
        String description) {
}
