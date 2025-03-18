package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.models.MedicineItem;
import io.github.cristian_eds.InfoMed.repository.MedicineItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MedicineItemService {

    private final MedicineItemRepository medicineItemRepository;

    public MedicineItem create(MedicineItem medicineItem) {
        return medicineItemRepository.save(medicineItem);
    }

    public MedicineItem findById(UUID id) {
        return medicineItemRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No Medicine Item found with this id."));
    }

    public List<MedicineItem> findAll() {
        return medicineItemRepository.findAll();
    }

    public void deleteById(UUID id) {
        medicineItemRepository.deleteById(id);
    }

    public MedicineItem alterStatusConclusion(UUID id) {
        Optional<MedicineItem> medicineItemFinded = medicineItemRepository.findById(id);
        if (medicineItemFinded.isEmpty()) return null;

        MedicineItem medicineItem = medicineItemFinded.get();
        medicineItem.setConclusion(!medicineItem.getConclusion());

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
