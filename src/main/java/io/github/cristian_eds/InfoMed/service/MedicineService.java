package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.models.User;
import io.github.cristian_eds.InfoMed.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final MedicineItemService medicineItemService;
    private final SecurityService securityService;

    @Transactional
    public Medicine create(Medicine medicine, LocalDateTime initialTime) {
        Medicine medicineSaved =  medicineRepository.save(medicine);
        User user = securityService.getAuthenticatedUser();
        medicineSaved.setUser(user);
        medicineItemService.generateItens(medicineSaved, initialTime);
        return medicineSaved;
    }

    public void deleteById(UUID id) {
        medicineRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No Medicine found with this Id"));
        medicineRepository.deleteById(id);
    }

    public List<Medicine> findAll() {
        return medicineRepository.findAll();
    }

    public Optional<Medicine> findById(UUID id) {
        return medicineRepository.findById(id);
    }

}
