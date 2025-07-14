package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.controller.common.GenerateURILocation;
import io.github.cristian_eds.InfoMed.controller.dto.*;
import io.github.cristian_eds.InfoMed.models.Person;
import io.github.cristian_eds.InfoMed.service.ImageService;
import io.github.cristian_eds.InfoMed.service.MedicineService;
import io.github.cristian_eds.InfoMed.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final MedicineService medicineService;
    private final ImageService imageService;

    @PostMapping
    @Transactional
    public ResponseEntity<PersonResponseDTO> create(
            @RequestParam String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) LocalDate birthDate,
            @RequestParam(required = false, value = "file") MultipartFile file){
        Person personSaved = personService.save(new CreatePersonDTO(name, birthDate, phone,""));
        if (!file.isEmpty() && personSaved.getId() != null) {
            String imageUrl = imageService.upload(file, personSaved.getId().toString());
            personSaved.setImageUrl(imageUrl);
        };

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
        PersonResponseDTO.fromEntity(personService.findById(uuid));
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
    public ResponseEntity<Void> deletePerson(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        personService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/accessCode")
    public ResponseEntity<AccessCodeDTO> generateAccessCode(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        return ResponseEntity.ok(new AccessCodeDTO(personService.generateCode(uuid)));
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<ImagePersonUrlDTO> updateImage(
            @PathVariable("id") String id,
            @RequestParam(required = true, value = "file") MultipartFile file) {
        return ResponseEntity.ok(new ImagePersonUrlDTO(personService.updateImage(file, id)));
    }

    @DeleteMapping("/{id}/image")
    public ResponseEntity<Void> deleteImage(
            @PathVariable("id") String id) {
        personService.deleteImage(id);
        return ResponseEntity.ok().build();
    }

}
