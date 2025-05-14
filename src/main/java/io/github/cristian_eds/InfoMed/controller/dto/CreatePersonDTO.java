package io.github.cristian_eds.InfoMed.controller.dto;

import io.github.cristian_eds.InfoMed.models.Person;
import jakarta.validation.constraints.NotBlank;

public record CreatePersonDTO (
        @NotBlank(message = "Insert a valid name")
        String name
){

    public static Person toEntity(CreatePersonDTO dto) {
        Person person = new Person();
        person.setName(dto.name());
        return person;
    }
}
