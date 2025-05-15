package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.controller.dto.CreatePersonDTO;
import io.github.cristian_eds.InfoMed.controller.dto.PersonResponseDTO;
import io.github.cristian_eds.InfoMed.models.Person;
import io.github.cristian_eds.InfoMed.models.User;
import io.github.cristian_eds.InfoMed.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final SecurityService securityService;

    public Person save(CreatePersonDTO personDto) {
        User user =  securityService.getAuthenticatedUser();
        Person person = CreatePersonDTO.toEntity(personDto);
        person.setUserFather(user);
        return  personRepository.save(person);
    }

    public Page<PersonResponseDTO> findAll(Pageable pageable) {
        return personRepository.findByUserFather(securityService.getAuthenticatedUser(), pageable);
    }

    public Optional<Person> findById(UUID id) {
        return personRepository.findByUserFatherAndId(securityService.getAuthenticatedUser(),id);
    }
}
