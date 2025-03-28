package io.github.cristian_eds.InfoMed.controller.dto;

import io.github.cristian_eds.InfoMed.models.MedicineItem;

import java.time.LocalDateTime;

public record MedicineItemUpdateDTO(
        LocalDateTime dayHour,
        Boolean conclusion,
        LocalDateTime conclusionDayHour
) {
    public static MedicineItem toEntity(MedicineItemUpdateDTO medicineItemUpdateDTO) {
        MedicineItem medicineItem = new MedicineItem();
        medicineItem.setConclusion(medicineItemUpdateDTO.conclusion);
        medicineItem.setDayHour(medicineItemUpdateDTO.dayHour);
        medicineItem.setConclusionDayHour(medicineItemUpdateDTO.conclusionDayHour);
        return medicineItem;
    }
}
