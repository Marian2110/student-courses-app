package org.example.model.dto;

import lombok.Builder;

@Builder
public record PageInfo(int currentPage, int pageSize, int totalPages, int totalElements) {
}
