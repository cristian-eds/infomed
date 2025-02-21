package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final MedicineItemService medicineItemService;

    @Transactional
    public Medicine create(Medicine medicine, LocalDateTime initialTime) {
        Medicine medicineSaved =  medicineRepository.save(medicine);
        medicineItemService.generateItens(medicineSaved, initialTime);
        return medicineSaved;
    }

}
