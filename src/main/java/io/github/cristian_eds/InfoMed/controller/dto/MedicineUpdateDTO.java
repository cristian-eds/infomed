package io.github.cristian_eds.InfoMed.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record MedicineUpdateDTO(
        @NotBlank(message = "Insert a valid name")
        String name
) {
}
