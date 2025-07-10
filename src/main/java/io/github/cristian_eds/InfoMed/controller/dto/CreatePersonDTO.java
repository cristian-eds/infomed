package io.github.cristian_eds.InfoMed.controller.dto;

import io.github.cristian_eds.InfoMed.models.Person;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreatePersonDTO (
        @NotBlank(message = "Insert a valid name")
        String name,
        LocalDate birthDate,
        String phone,
        String imageUrl
){

    public static Person toEntity(CreatePersonDTO dto) {
        Person person = new Person();
        person.setName(dto.name());
        person.setPhone(dto.phone());
        person.setBirthDate(dto.birthDate());
        person.setImageUrl(dto.imageUrl);
        return person;
    }
}
