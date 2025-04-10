package io.github.cristian_eds.InfoMed.controller.dto;

import io.github.cristian_eds.InfoMed.models.User;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String email,
        String name
) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(user.getId(),user.getEmail(),user.getName());
    }
}
