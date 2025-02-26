package io.github.cristian_eds.InfoMed.controller.dto;

import io.github.cristian_eds.InfoMed.models.enums.ActionType;

public record HistoricLogSaveDTO(
        ActionType action,
        String description,
        Integer statusCode
) {
}
