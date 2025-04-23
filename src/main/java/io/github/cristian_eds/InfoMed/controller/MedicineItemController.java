package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.controller.dto.CustomMedicineItemDTO;
import io.github.cristian_eds.InfoMed.controller.dto.MedicineItemResponseDTO;
import io.github.cristian_eds.InfoMed.controller.dto.MedicineItemUpdateDTO;
import io.github.cristian_eds.InfoMed.controller.dto.MedicineResponseDTO;
import io.github.cristian_eds.InfoMed.service.MedicineItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/medicine/item")
@RequiredArgsConstructor
public class MedicineItemController {

    private final MedicineItemService medicineItemService;

    @PutMapping("/{idItem}/status")
    public ResponseEntity<MedicineItemResponseDTO> alterStatusConclusion(@PathVariable("idItem") String id) {
        UUID uuid = UUID.fromString(id);
        MedicineItemResponseDTO medicineItemResponseDTO = MedicineItemResponseDTO.fromEntity(medicineItemService.alterStatusConclusion(uuid));
        return ResponseEntity.ok(medicineItemResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicineItemResponseDTO> update(@PathVariable("id") String id, @RequestBody MedicineItemUpdateDTO medicineItemUpdateDTO) {
        UUID uuid = UUID.fromString(id);
        MedicineItemResponseDTO medicineItemResponseDTO = MedicineItemResponseDTO.fromEntity(medicineItemService.update(uuid,medicineItemUpdateDTO));
        return ResponseEntity.ok(medicineItemResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineItemResponseDTO> getById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        MedicineItemResponseDTO medicineItemResponseDTO = MedicineItemResponseDTO.fromEntity(medicineItemService.findById(uuid));
        return ResponseEntity.ok(medicineItemResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<CustomMedicineItemDTO>> getAllWithCustomPage(@RequestParam(value = "name", required = false,defaultValue = "") String name,
                                                                            @RequestParam(value = "actualPage", required = false, defaultValue = "0") int actualPage,
                                                                            @RequestParam(value = "sizePage", required = false, defaultValue = "6") int sizePage,
                                                                            @RequestParam(value = "initialDate", required = false) String initialDate,
                                                                            @RequestParam(value = "finalDate", required = false) String finalDate,
                                                                            @RequestParam(value = "conclusion", required = false) String conclusion) {
        return ResponseEntity.ok(medicineItemService.findAllWithCustomPage(name,actualPage,sizePage, initialDate,finalDate,conclusion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        medicineItemService.deleteById(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/next")
    public ResponseEntity<MedicineItemResponseDTO> getNextMedicineItem() {
        return medicineItemService
                .findNextMedicine().map(
                        medicineItem ->
                                ResponseEntity.ok(MedicineItemResponseDTO.fromEntity(medicineItem))).orElse(
                                ResponseEntity.noContent().build()
        );
    }

}
