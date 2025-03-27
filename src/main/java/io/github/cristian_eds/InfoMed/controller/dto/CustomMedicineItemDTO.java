package io.github.cristian_eds.InfoMed.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
        LocalDateTime conclusionDayHour

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
                        medicineItem.conclusionDayHour()
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
                                                    medicineItem.conclusionDayHour()
                                            )
                                    )
                    );

                }

        );
        return listCustoms;
    }
}
