package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
}
