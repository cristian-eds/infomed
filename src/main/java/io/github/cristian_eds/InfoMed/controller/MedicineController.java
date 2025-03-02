package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.controller.common.GenerateURILocation;
import io.github.cristian_eds.InfoMed.controller.dto.CreateMedicineDTO;
import io.github.cristian_eds.InfoMed.controller.dto.MedicineResponseDTO;
import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.service.MedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/medicine")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid CreateMedicineDTO createMedicineDTO) {
        LocalDateTime initialLocalDateTime = LocalDateTime.parse(createMedicineDTO.initialDateTime());

        Medicine medicine = CreateMedicineDTO.toEntity(createMedicineDTO);
        Medicine medicineSaved = medicineService.create(medicine,initialLocalDateTime);

        URI location = GenerateURILocation.generateURI(medicineSaved.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestParam(value = "name", required = false) String name) {
        List<MedicineResponseDTO> medicines = medicineService.findAll(name).stream().map(MedicineResponseDTO::fromEntity).toList();
        return ResponseEntity.ok(medicines);
    }

    @GetMapping("{id}")
    public ResponseEntity<MedicineResponseDTO> getById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        return medicineService.findById(uuid).map(
                medicine -> {
                        return ResponseEntity.ok(MedicineResponseDTO.fromEntity(medicine));
                }
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        medicineService.deleteById(uuid);
        return ResponseEntity.ok().build();
    }
}
