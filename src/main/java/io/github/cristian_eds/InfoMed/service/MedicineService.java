package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.controller.dto.CustomMedicineItemDTO;
import io.github.cristian_eds.InfoMed.controller.dto.MedicineResponseDTO;
import io.github.cristian_eds.InfoMed.controller.dto.MedicineUpdateDTO;
import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.models.MedicineItem;
import io.github.cristian_eds.InfoMed.models.User;
import io.github.cristian_eds.InfoMed.repository.MedicineRepository;
import io.github.cristian_eds.InfoMed.repository.specs.MedicineSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
        List<MedicineItem> items = medicineItemService.generateItens(medicineSaved, initialTime);
        medicineSaved.setMedicineItems(items);
        return medicineSaved;
    }

    public void deleteById(UUID id) {
        medicineRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No Medicine found with this Id"));
        medicineRepository.deleteById(id);
    }

    public Page<CustomMedicineItemDTO> findAll(String name, int actualPage, int sizePage) {
        Specification<Medicine> specs = Specification.where(
                (root, query, criteriaBuilder) ->
                        criteriaBuilder.conjunction());
        if (name != null) specs = specs.and(MedicineSpecs.nameLike(name));
        User user = securityService.getAuthenticatedUser();
        specs = specs.and(MedicineSpecs.userEquals(user));

        Pageable pageable = PageRequest.of(actualPage,sizePage);
        Page<Medicine> medicinePage = medicineRepository.findAll(specs,pageable);
        return convertPageMedicineToPageMedicineResponseDTO(medicinePage,pageable);
    }

    public Page<CustomMedicineItemDTO> findAllWithCustomPage(String name, int actualPage, int sizePage) {
        Pageable pageable = PageRequest.of(actualPage,sizePage);
        User user = securityService.getAuthenticatedUser();
        return medicineRepository.findCustomMedicineItemsWithPagination(name.toUpperCase(), user, pageable);
    }

    public Optional<Medicine> findById(UUID id) {
        return medicineRepository.findById(id);
    }

    public Medicine update(UUID id, MedicineUpdateDTO medicineUpdateDTO) {
        Medicine medicine = findById(id).orElseThrow(() -> new NoSuchElementException("No Medicine found with this Id"));
        medicine.setName(medicineUpdateDTO.name());
        return medicineRepository.save(medicine);
    }

    private Page<CustomMedicineItemDTO> convertPageMedicineToPageMedicineResponseDTO(Page<Medicine> medicinesPage, Pageable pageable) {
        List<MedicineResponseDTO> listMedicinesDTOs = medicinesPage.getContent().stream().map(
                MedicineResponseDTO::fromEntity).toList();
        List<CustomMedicineItemDTO> listCustomMedicineItems = CustomMedicineItemDTO.fromListMedicineResponseDTO(listMedicinesDTOs);

        return new PageImpl<>(listCustomMedicineItems, pageable, medicinesPage.getTotalElements());
    }

}
