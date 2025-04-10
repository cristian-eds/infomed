package io.github.cristian_eds.InfoMed.controller.dto;

import io.github.cristian_eds.InfoMed.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @Email(message = "Insert a valid email")
        @NotBlank(message = "Insert a valid email")
                @Size(max = 200, message = "Insert a valid email.")
        String email,

        @Size(max = 300, min = 4)
                @NotBlank(message = "Insert a valid password.")
        String password,
        @Size(max = 150, message = "Insert a valid name.")
        @NotBlank(message = "Insert a valid name.")
        String name
){

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.email);
        user.setPassword(userDTO.password);
        user.setName(userDTO.name);
        return user;
    }
}
