package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.controller.dto.*;
import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.models.MedicineItem;
import io.github.cristian_eds.InfoMed.models.Person;
import io.github.cristian_eds.InfoMed.models.User;
import io.github.cristian_eds.InfoMed.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final SecurityService securityService;
    private final MedicineItemService medicineItemService;

    public Person save(CreatePersonDTO personDto) {
        User user =  securityService.getAuthenticatedUser();
        Person person = CreatePersonDTO.toEntity(personDto);
        person.setUserFather(user);
        return  personRepository.save(person);
    }

    public void createWhenRegistering(User user) {
        Person person = new Person();
        person.setName(user.getName());
        person.setUserFather(user);
        personRepository.save(person);
    }

    public PagedResponseDTO<CustomPersonResponseDTO> findAll(Pageable pageable) {
        Page<Person> pageResult = personRepository.findByUserFather(securityService.getAuthenticatedUser(), pageable);
        List<CustomPersonResponseDTO> responseDTOList = pageResult.getContent().stream().map(
                person -> {
                    MedicineItem medicineItem = medicineItemService.findNextMedicinePerson(person).orElseGet(() -> null);
                    CustomMedicineItemDTO customMedicineItemDTO = medicineItem != null ? CustomMedicineItemDTO.fromMedicineItem(medicineItem) : null;
                    return  CustomPersonResponseDTO.fromEntity(person, customMedicineItemDTO);
                }
        ).toList();

        return new PagedResponseDTO<>(
                responseDTOList,
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.getNumber(),
                pageResult.getSize()
        );
    }

    public Person findById(UUID id) {
        return personRepository.findByUserFatherAndId(securityService.getAuthenticatedUser(),id).orElseThrow(() -> new NoSuchElementException("Person not found"));
    }

    public Person update(UUID id, UpdatePersonDTO updatePersonDTO) {
        Person person = findById(id);
        person.setName(updatePersonDTO.name());
        person.setPhone(updatePersonDTO.phone());
        person.setBirthDate(updatePersonDTO.birthDate());
        return personRepository.save(person);
    }

    @Transactional
    public List<MedicinePersonDTO> getMedicines(UUID id, final MedicineService medicineService) {
        Person person = findById(id);
        User user =  securityService.getAuthenticatedUser();
        List<Medicine> medicines = medicineService.findByPerson(person, user);

        return medicines.stream().map(medicine -> {
            CustomMedicineItemDTO itemDTO =
                    medicineItemService.findNextItemPendingByMedicine(medicine)
                            .map(CustomMedicineItemDTO::fromMedicineItem).orElseGet(() -> null);
            return MedicinePersonDTO.fromEntityAndNextItem(medicine, itemDTO);
        }).toList();
    }

    public void delete(UUID uuid) {
        Person person = findById(uuid);
        personRepository.delete(person);
    }
}
