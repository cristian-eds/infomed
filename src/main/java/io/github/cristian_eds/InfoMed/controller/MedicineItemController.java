package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.controller.dto.MedicineItemResponseDTO;
import io.github.cristian_eds.InfoMed.models.MedicineItem;
import io.github.cristian_eds.InfoMed.service.MedicineItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/medicine/item")
@RequiredArgsConstructor
public class MedicineItemController {

    private final MedicineItemService medicineItemService;

    @PutMapping("/{idItem}/status")
    public ResponseEntity<Void> alterStatusConclusion(@PathVariable("idItem") String id) {
        UUID uuid = UUID.fromString(id);
        medicineItemService.alterStatusConclusion(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineItemResponseDTO> getById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        MedicineItemResponseDTO medicineItemResponseDTO = MedicineItemResponseDTO.fromEntity(medicineItemService.findById(uuid));
        return ResponseEntity.ok(medicineItemResponseDTO);
    }

    @GetMapping
    public  ResponseEntity<List<MedicineItemResponseDTO>> getAll() {
        List<MedicineItemResponseDTO> medicineItemResponseDTOS = medicineItemService.findAll().stream().map(MedicineItemResponseDTO::fromEntity).toList();
        return ResponseEntity.ok(medicineItemResponseDTOS);
    }

}
