package io.github.cristian_eds.InfoMed.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(
        @Email(message = "Insert a valid email")
        @NotBlank(message = "Insert a valid email")
        @Size(max = 200, message = "Insert a valid email.")
        String email,
        @Size(max = 150, message = "Insert a valid name.")
        @NotBlank(message = "Insert a valid name.")
        String name
) {
}
