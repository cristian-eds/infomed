package io.github.cristian_eds.InfoMed.controller.dto;

import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.models.MedicineItem;

import java.util.List;
import java.util.UUID;

public record MedicineResponseDTO(
        UUID id,
        String name,
        Double totalDays,
        Double frequencyHours,
        List<MedicineItemResponseDTO> medicineItems
) {

    public static MedicineResponseDTO fromEntity(Medicine medicine) {
        return new MedicineResponseDTO(
                medicine.getId(),
                medicine.getName(),
                medicine.getTotalDays(),
                medicine.getFrequencyHours(),
                medicine.getMedicineItems().stream().map(MedicineItemResponseDTO::fromEntity).toList()
        );
    }
}
