package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.controller.common.GenerateURILocation;
import io.github.cristian_eds.InfoMed.controller.dto.*;
import io.github.cristian_eds.InfoMed.models.Person;
import io.github.cristian_eds.InfoMed.service.MedicineService;
import io.github.cristian_eds.InfoMed.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final MedicineService medicineService;

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
        return ResponseEntity.ok(PersonResponseDTO.fromEntity(personService.findById(uuid)));
    }

    @PutMapping("{id}")
    public ResponseEntity<PersonResponseDTO> update(
            @PathVariable("id") String id,
            @RequestBody UpdatePersonDTO updatePersonDTO) {
        UUID uuid = UUID.fromString(id);
        return  ResponseEntity.ok(PersonResponseDTO.fromEntity(personService.update(uuid,updatePersonDTO)));
    }

    @GetMapping("/{id}/medicines")
    public ResponseEntity<List<MedicinePersonDTO>> getMedicines(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        return ResponseEntity.ok(personService.getMedicines(uuid, medicineService));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        personService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/accessCode")
    public ResponseEntity<AccessCodeDTO> generateAccessCode(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        return ResponseEntity.ok(new AccessCodeDTO(personService.generateCode(uuid)));
    }

}
