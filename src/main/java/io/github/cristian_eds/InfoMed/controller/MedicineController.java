package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.controller.dto.CreateMedicineDTO;
import io.github.cristian_eds.InfoMed.controller.dto.MedicineItemResponseDTO;
import io.github.cristian_eds.InfoMed.controller.dto.MedicineResponseDTO;
import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.service.MedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
        return ResponseEntity.ok(medicineSaved);
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<MedicineResponseDTO> medicines = medicineService.findAll().stream().map(MedicineResponseDTO::fromEntity).toList();
        return ResponseEntity.ok(medicines);
    }
}
