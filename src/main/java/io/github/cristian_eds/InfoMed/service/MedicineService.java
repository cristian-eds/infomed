package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.controller.dto.CustomMedicineItemDTO;
import io.github.cristian_eds.InfoMed.controller.dto.MedicineResponseDTO;
import io.github.cristian_eds.InfoMed.controller.dto.MedicineUpdateDTO;
import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.models.MedicineItem;
import io.github.cristian_eds.InfoMed.models.Person;
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

import java.sql.SQLOutput;
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
    private final PersonService personService;

    @Transactional
    public Medicine create(Medicine medicine, LocalDateTime initialTime) {
        User user = securityService.getAuthenticatedUser();
        medicine.setUser(user);

        if (medicine.getPerson() != null) {
            Person person = personService.findById(medicine.getPerson().getId());
            medicine.setPerson(person);
        }

        Medicine medicineSaved =  medicineRepository.save(medicine);

        List<MedicineItem> items = medicineItemService.generateItens(medicineSaved, initialTime);
        medicineSaved.setMedicineItems(items);

        return medicineSaved;
    }

    public void deleteById(UUID id) {
        medicineRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No Medicine found with this Id"));
        medicineRepository.deleteById(id);
    }

    public Page<MedicineResponseDTO> findAll(String name, int actualPage, int sizePage) {
        Specification<Medicine> specs = Specification.where(null);

        User user = securityService.getAuthenticatedUser();
        System.out.println(user);
        specs = specs.and(MedicineSpecs.userOrPersonAccessCodeLike(user, user.getName().toUpperCase()));

        if (name != null) {
            specs = specs.and(MedicineSpecs.nameOrPersonNameLike(name));
        }

        Pageable pageable = PageRequest.of(actualPage,sizePage);

        Page<Medicine> medicinePage = medicineRepository.findAll(specs,pageable);
        return convertPageMedicineToPageMedicineResponseDTO(medicinePage,pageable);
    }

    public Optional<Medicine> findById(UUID id) {
        return medicineRepository.findById(id);
    }

    public Medicine update(UUID id, MedicineUpdateDTO medicineUpdateDTO) {
        Medicine medicine = findById(id).orElseThrow(() -> new NoSuchElementException("No Medicine found with this Id"));
        medicine.setName(medicineUpdateDTO.name());
        if(medicineUpdateDTO.idPerson() != null) {
            Person person = personService.findById(UUID.fromString(medicineUpdateDTO.idPerson()));
            if (!person.equals(medicine.getPerson())) {
                medicine.setPerson(person);
            }
        }
        return medicineRepository.save(medicine);
    }

    private Page<MedicineResponseDTO> convertPageMedicineToPageMedicineResponseDTO(Page<Medicine> medicinesPage, Pageable pageable) {
        List<MedicineResponseDTO> listMedicinesDTOs = medicinesPage.getContent().stream().map(
                MedicineResponseDTO::fromEntity).toList();

        return new PageImpl<>(listMedicinesDTOs, pageable, medicinesPage.getTotalElements());
    }

    public List<Medicine> findByPerson(Person person,User user) {
        return medicineRepository.findByPersonAndUser(person,user);
    }

}
