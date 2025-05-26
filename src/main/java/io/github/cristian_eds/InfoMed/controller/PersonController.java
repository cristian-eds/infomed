package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.controller.common.GenerateURILocation;
import io.github.cristian_eds.InfoMed.controller.dto.*;
import io.github.cristian_eds.InfoMed.models.Person;
import io.github.cristian_eds.InfoMed.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<PersonResponseDTO> create(@RequestBody @Valid CreatePersonDTO person){
        Person personSaved = personService.save(person);
        URI location = GenerateURILocation.generateURI(personSaved.getId());
        return ResponseEntity.created(location).body(PersonResponseDTO.fromEntity(personSaved));
    }

    @GetMapping
    public ResponseEntity<PagedResponseDTO<CustomPersonResponseDTO>> getAll(PaginationRequestDTO paginationRequest) {
        Pageable pageable = PageRequest.of(paginationRequest.actualPage(), paginationRequest.sizePage());
        return ResponseEntity.ok(personService.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonResponseDTO> getById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        return personService.findById(uuid).map(
                person -> ResponseEntity.ok(PersonResponseDTO.fromEntity(person)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<PersonResponseDTO> update(
            @PathVariable("id") String id,
            @RequestBody UpdatePersonDTO updatePersonDTO) {
        UUID uuid = UUID.fromString(id);
        return  ResponseEntity.ok(PersonResponseDTO.fromEntity(personService.update(uuid,updatePersonDTO)));
    }
}
