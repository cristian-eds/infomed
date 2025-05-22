package io.github.cristian_eds.InfoMed.controller.dto;

import io.github.cristian_eds.InfoMed.models.MedicineItem;
import io.github.cristian_eds.InfoMed.models.Person;

import java.util.UUID;

public record CustomPersonResponseDTO(
        UUID id,
        String name,
        Integer totalMedicines,
        Integer pendingMedicines,
        CustomMedicineItemDTO nextMedicine
) {

    public static CustomPersonResponseDTO fromEntity(Person person, CustomMedicineItemDTO nextMedicine) {
        int concludedMedicines = person.getMedicines().stream()
                .reduce(0, (integer, medicine) ->
                                medicine.getMedicineItems().stream().allMatch(
                                                MedicineItem::getConclusion) ? integer + 1 : integer, Integer::sum
                );

        int totalMedicines = person.getMedicines().size();

        return new CustomPersonResponseDTO(
                person.getId(),
                person.getName(),
                totalMedicines,
                totalMedicines - concludedMedicines,
                nextMedicine
        );
    }
}
