package io.github.cristian_eds.InfoMed.controller.dto;

import java.util.List;

public record PagedResponseDTO<T>(
        List<T> content,
        long totalElements,
        int totalPages,
        int currentPage,
        int pageSize
) {
}
