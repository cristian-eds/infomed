package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.controller.dto.CreateMedicineDTO;
import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.service.MedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicine")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid CreateMedicineDTO createMedicineDTO) {
        Medicine medicine = CreateMedicineDTO.toEntity(createMedicineDTO);
        Medicine medicineSaved = medicineService.create(medicine);
        return ResponseEntity.ok(medicineSaved);
    }
}
