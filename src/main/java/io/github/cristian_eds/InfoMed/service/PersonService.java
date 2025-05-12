package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
}
