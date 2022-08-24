package org.example.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CollectionResponse<T>(List<T> content, PageInfo pageInfo) {
}
