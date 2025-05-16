package io.github.cristian_eds.InfoMed.controller.dto;

import io.github.cristian_eds.InfoMed.models.Person;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record PersonResponseDTO (
        UUID id,
        String name,
        String birthDate,
        String phone,
        String accessCode
) {

    public static PersonResponseDTO fromEntity(Person person) {
        return new
                PersonResponseDTO(
                        person.getId(),
                        person.getName(),
                        person.getBirthDate() != null ? person.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")): "",
                        person.getPhone(),
                        person.getAccessCode());
    }
}
