package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StudentCourse(
        String name,
        Integer age,
        String discipline,
        Integer grade) {
}
