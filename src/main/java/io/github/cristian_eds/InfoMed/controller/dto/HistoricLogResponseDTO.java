package io.github.cristian_eds.InfoMed.controller.dto;

import io.github.cristian_eds.InfoMed.models.enums.ActionType;

import java.util.UUID;

public record HistoricLogResponseDTO(
        UUID id,
        ActionType action,
        String description,
        Integer statusCode
) {
}
