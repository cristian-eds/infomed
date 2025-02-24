package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.service.MedicineItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/medicine/{idMedicine}/item")
@RequiredArgsConstructor
public class MedicineItemController {

    private final MedicineItemService medicineItemService;

    @PutMapping("/{idItem}/status")
    public ResponseEntity<Void> alterStatusConclusion(@PathVariable("idItem") String id) {
        UUID uuid = UUID.fromString(id);
        medicineItemService.alterStatusConclusion(uuid);
        return ResponseEntity.noContent().build();
    }

}
