package io.github.cristian_eds.InfoMed.controller.dto;

import io.github.cristian_eds.InfoMed.models.MedicineItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record CustomMedicineItemDTO(
        UUID medicineId,
        UUID medicineItemId,
        String name,
        int sequency,
        int total,
        Double frequency,
        LocalDateTime dayHour,
        boolean conclusion,
        LocalDateTime conclusionDayHour,
        String personName

) {
    public static List<CustomMedicineItemDTO> fromMedicineResponseDTO(MedicineResponseDTO medicineResponseDTO) {
        return medicineResponseDTO.medicineItems().stream()
                .map(medicineItem -> new CustomMedicineItemDTO(
                        medicineResponseDTO.id(),
                        medicineItem.id(),
                        medicineResponseDTO.name(),
                        medicineItem.medicineItemSequence(),
                        medicineResponseDTO.medicineItems().size(),
                        medicineResponseDTO.frequencyHours(),
                        medicineItem.dayHour(),
                        medicineItem.conclusion(),
                        medicineItem.conclusionDayHour(),
                        medicineResponseDTO.person().name()
                )).toList();
    }

    public static List<CustomMedicineItemDTO> fromListMedicineResponseDTO(List<MedicineResponseDTO> medicinesResponseDTO) {
        List<CustomMedicineItemDTO> listCustoms = new ArrayList<>();
        medicinesResponseDTO.forEach(
                medicine -> {
                    medicine.medicineItems().forEach(
                            medicineItem ->
                                    listCustoms.add(
                                            new CustomMedicineItemDTO(
                                                    medicine.id(),
                                                    medicineItem.id(),
                                                    medicine.name(),
                                                    medicineItem.medicineItemSequence(),
                                                    medicine.medicineItems().size(),
                                                    medicine.frequencyHours(),
                                                    medicineItem.dayHour(),
                                                    medicineItem.conclusion(),
                                                    medicineItem.conclusionDayHour(),
                                                    medicine.person().name()
                                            )
                                    )
                    );

                }

        );
        return listCustoms;
    }

    public static CustomMedicineItemDTO fromMedicineItem(MedicineItem medicineItem) {
        return new CustomMedicineItemDTO(
                medicineItem.getMedicine().getId(),
                medicineItem.getId(),
                medicineItem.getMedicine().getName(),
                medicineItem.getMedicineItemSequence(),
                medicineItem.getMedicine().getMedicineItems().size(),
                medicineItem.getMedicine().getFrequencyHours(),
                medicineItem.getDayHour(),
                medicineItem.getConclusion(),
                medicineItem.getConclusionDayHour(),
                medicineItem.getMedicine().getPerson() != null ? medicineItem.getMedicine().getPerson().getName() : ""
        );
    }
}
