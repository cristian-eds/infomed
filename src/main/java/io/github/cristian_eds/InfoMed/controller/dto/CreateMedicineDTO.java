package io.github.cristian_eds.InfoMed.controller.dto;

import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.models.Person;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateMedicineDTO(
        @NotBlank(message = "Insert a valid name")
        String name,
        @NotNull(message = "Insert a valid total days")
        Double totalDays,
        @NotNull(message = "Insert a valid frequency")
        Double frequencyHours,
        @NotNull(message = "Insert a valid datetime")
        String initialDateTime,
        @NotNull(message = "Insert a valid person")
        String idPerson

) {

    public static Medicine toEntity(CreateMedicineDTO medicineDTO) {
        Medicine medicine = new Medicine();
        Person person = new Person();
        person.setId(UUID.fromString(medicineDTO.idPerson));
        medicine.setName(medicineDTO.name());
        medicine.setFrequencyHours(medicineDTO.frequencyHours());
        medicine.setTotalDays(medicineDTO.totalDays());
        medicine.setPerson(person);
        return medicine;
    }
}
