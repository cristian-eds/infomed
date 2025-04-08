package io.github.cristian_eds.InfoMed.controller.dto;

import io.github.cristian_eds.InfoMed.models.User;

public record UserResponseDTO(
        String email,
        String name
) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(user.getEmail(),user.getName());
    }
}
