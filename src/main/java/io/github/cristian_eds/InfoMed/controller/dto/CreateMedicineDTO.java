package io.github.cristian_eds.InfoMed.controller.dto;

import io.github.cristian_eds.InfoMed.models.Medicine;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMedicineDTO(
        @NotBlank(message = "Insert a valid name")
        String name,
        @NotNull(message = "Insert a valid total days")
        Double totalDays,
        @NotNull(message = "Insert a valid frequency")
        Double frequencyHours,
        @NotNull(message = "Insert a valid datetime")
        String initialDateTime

) {

    public static Medicine toEntity(CreateMedicineDTO medicineDTO) {
        Medicine medicine = new Medicine();
        medicine.setName(medicineDTO.name);
        medicine.setFrequencyHours(medicineDTO.frequencyHours);
        medicine.setTotalDays(medicineDTO.totalDays);
        return medicine;
    }
}
