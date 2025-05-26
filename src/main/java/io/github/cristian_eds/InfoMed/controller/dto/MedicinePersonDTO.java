package io.github.cristian_eds.InfoMed.controller.dto;

import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.models.MedicineItem;

import java.time.LocalDateTime;

public record MedicinePersonDTO(
        String name,
        LocalDateTime registrationDate,
        Double totalDays,
        Double frequence,
        boolean concluded,
        CustomMedicineItemDTO nextItem
) {

    public static MedicinePersonDTO fromEntityAndNextItem(Medicine medicine, CustomMedicineItemDTO  nextItemDTO) {
        return new MedicinePersonDTO(
                medicine.getName(),
                medicine.getRegistrationDate(),
                medicine.getTotalDays(),
                medicine.getFrequencyHours(),
                medicine.getMedicineItems().stream().allMatch(MedicineItem::getConclusion),
                nextItemDTO
        );
    }
}
