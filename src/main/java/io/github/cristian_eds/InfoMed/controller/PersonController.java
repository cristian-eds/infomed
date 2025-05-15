package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.controller.dto.CreatePersonDTO;
import io.github.cristian_eds.InfoMed.controller.dto.PersonResponseDTO;
import io.github.cristian_eds.InfoMed.models.Person;
import io.github.cristian_eds.InfoMed.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<PersonResponseDTO> create(@RequestBody @Valid CreatePersonDTO person){
        return ResponseEntity.ok(PersonResponseDTO.fromEntity(personService.save(person)));
    }

    @GetMapping
    public ResponseEntity<List<PersonResponseDTO>> getAll() {
        List<PersonResponseDTO> list = personService.findAll().stream().map(PersonResponseDTO::fromEntity).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonResponseDTO> getById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        return personService.findById(uuid).map(
                person -> ResponseEntity.ok(PersonResponseDTO.fromEntity(person)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
