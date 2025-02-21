package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.models.MedicineItem;
import io.github.cristian_eds.InfoMed.repository.MedicineItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineItemService {

    private final MedicineItemRepository medicineItemRepository;

    public MedicineItem create(MedicineItem medicineItem) {
        return medicineItemRepository.save(medicineItem);
    }

    public List<MedicineItem> generateItens(
            Medicine medicine, LocalDateTime initialTime) {
        int quantityOfTotalItens = calculateTotalItens(medicine.getTotalDays(),medicine.getFrequencyHours());

        List<MedicineItem> listMedicineItens = new ArrayList<>();
        LocalDateTime dayHourTime = initialTime;

        for (int i = 1; i <= quantityOfTotalItens; i++) {
            MedicineItem medicineItem = new MedicineItem();
            medicineItem.setConclusion(false);
            medicineItem.setDayHour(dayHourTime);
            medicineItem.setMedicineItemSequence(i);
            medicineItem.setMedicine(medicine);

            listMedicineItens.add(medicineItem);

            dayHourTime = dayHourTime.plusHours(medicine.getFrequencyHours().longValue());
        }

        medicineItemRepository.saveAll(listMedicineItens);

        return listMedicineItens;
    }

    private int calculateTotalItens(double totalDays, double frequenceHour) {
        return (int) Math.floor(totalDays * 24 / frequenceHour);
    }

}
