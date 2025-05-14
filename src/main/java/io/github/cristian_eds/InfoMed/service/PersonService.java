package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.controller.dto.CreatePersonDTO;
import io.github.cristian_eds.InfoMed.models.Person;
import io.github.cristian_eds.InfoMed.models.User;
import io.github.cristian_eds.InfoMed.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Person> findAll() {

    }
}
