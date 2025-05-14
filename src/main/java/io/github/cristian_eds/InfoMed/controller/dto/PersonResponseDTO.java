package io.github.cristian_eds.InfoMed.controller.dto;

import io.github.cristian_eds.InfoMed.models.Person;

import java.util.UUID;

public record PersonResponseDTO (
        UUID id,
        String name,
        String accessCode
) {

    public static PersonResponseDTO fromEntity(Person person) {
        return new PersonResponseDTO(person.getId(), person.getName(), person.getAccessCode());
    }
}
