package io.github.cristian_eds.InfoMed.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangeUserPasswordDTO(
        @NotBlank(message = "Insert an valid password.")
        String currentPassword,
        @NotBlank(message = "Insert an valid password.")
        String newPassword
) {
}
