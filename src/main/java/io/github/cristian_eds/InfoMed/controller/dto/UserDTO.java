package io.github.cristian_eds.InfoMed.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @Email(message = "Insert an valid email")
        @NotBlank(message = "Insert an valid email")
                @Size(max = 200, message = "Insert an valid email.")
        String email,

        @Size(max = 300)
                @NotBlank(message = "Insert an valid password.")
        String password) {
}
