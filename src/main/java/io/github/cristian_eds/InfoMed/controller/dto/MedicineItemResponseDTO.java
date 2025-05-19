package io.github.cristian_eds.InfoMed.controller.dto;

import io.github.cristian_eds.InfoMed.models.MedicineItem;

import java.time.LocalDateTime;
import java.util.UUID;

public record MedicineItemResponseDTO(
        UUID id,
        Integer medicineItemSequence,
        LocalDateTime dayHour,
        Boolean conclusion,
        LocalDateTime conclusionDayHour,
        String name,
        UUID medicine
) {
    public static MedicineItemResponseDTO fromEntity(MedicineItem medicineItem) {
        return new MedicineItemResponseDTO(
                medicineItem.getId(),
                medicineItem.getMedicineItemSequence(),
                medicineItem.getDayHour(),
                medicineItem.getConclusion(),
                medicineItem.getConclusionDayHour(),
                medicineItem.getMedicine().getName(),
                medicineItem.getMedicine().getId()
        );
    }
}
