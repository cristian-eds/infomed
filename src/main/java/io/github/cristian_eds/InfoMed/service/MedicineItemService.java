package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.controller.dto.CustomMedicineItemDTO;
import io.github.cristian_eds.InfoMed.controller.dto.MedicineItemUpdateDTO;
import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.models.MedicineItem;
import io.github.cristian_eds.InfoMed.models.User;
import io.github.cristian_eds.InfoMed.repository.MedicineItemRepository;
import io.github.cristian_eds.InfoMed.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MedicineItemService {

    private final MedicineItemRepository medicineItemRepository;
    private final MedicineRepository medicineRepository;
    private final SecurityService securityService;

    public MedicineItem create(MedicineItem medicineItem) {
        return medicineItemRepository.save(medicineItem);
    }

    public MedicineItem findById(UUID id) {
        return medicineItemRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No Medicine Item found with this id."));
    }

    public Page<CustomMedicineItemDTO> findAllWithCustomPage(String name, int actualPage, int sizePage, String initialDate, String finalDate, String conclusion) {
        Pageable pageable = PageRequest.of(actualPage,sizePage);
        User user = securityService.getAuthenticatedUser();

        if (initialDate!= null && finalDate != null) {
            LocalDateTime startDateTime = LocalDateTime.parse(initialDate);
            LocalDateTime finalDateTime = LocalDateTime.parse(finalDate);
            if(conclusion != null) {
                return medicineRepository.findCustomMedicineItemsWithPagination(name.toUpperCase(),user,Boolean.parseBoolean(conclusion),startDateTime,finalDateTime,pageable);
            }
            return medicineRepository.findCustomMedicineItemsWithPagination(name.toUpperCase(),user,startDateTime,finalDateTime, pageable);
        }
        if(conclusion != null ) {
            return medicineRepository.findCustomMedicineItemsWithPagination(name.toUpperCase(), user, Boolean.parseBoolean(conclusion), pageable);
        }
        return medicineRepository.findCustomMedicineItemsWithPagination(name.toUpperCase(), user, pageable);
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
        if(medicineItem.getConclusion()) {
            medicineItem.setConclusionDayHour(null);
        } else {
            medicineItem.setConclusionDayHour(LocalDateTime.now());
        }
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
            medicineItem.setConclusionDayHour(null);

            listMedicineItens.add(medicineItem);

            dayHourTime = dayHourTime.plusHours(medicine.getFrequencyHours().longValue());
        }

        medicineItemRepository.saveAll(listMedicineItens);

        return listMedicineItens;
    }

    public MedicineItem update(UUID uuid, MedicineItemUpdateDTO medicineItemUpdateDTO) {
       MedicineItem medicineItem = findById(uuid);
       medicineItem.setDayHour(medicineItemUpdateDTO.dayHour());
       medicineItem.setConclusion(medicineItemUpdateDTO.conclusion());
       medicineItem.setConclusionDayHour(medicineItemUpdateDTO.conclusionDayHour());
       return medicineItemRepository.save(medicineItem);
    }

    private int calculateTotalItens(double totalDays, double frequencyHour) {
        return (int) Math.floor(totalDays * 24 / frequencyHour);
    }


}
