package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.controller.common.GenerateURILocation;
import io.github.cristian_eds.InfoMed.controller.dto.*;
import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.models.enums.FieldSortMedicineItem;
import io.github.cristian_eds.InfoMed.models.enums.TypeSort;
import io.github.cristian_eds.InfoMed.service.MedicineItemService;
import io.github.cristian_eds.InfoMed.service.MedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/medicine/item")
@RequiredArgsConstructor
public class MedicineItemController {

    private final MedicineItemService medicineItemService;
    private final MedicineService medicineService;

    @PostMapping
    public ResponseEntity<List<CustomMedicineItemDTO>> create(@RequestBody @Valid CreateMedicineDTO createMedicineDTO) {
        LocalDateTime initialLocalDateTime = LocalDateTime.parse(createMedicineDTO.initialDateTime());

        Medicine medicine = CreateMedicineDTO.toEntity(createMedicineDTO);
        MedicineResponseDTO medicineSaved = MedicineResponseDTO.fromEntity(medicineService.create(medicine,initialLocalDateTime));

        URI location = GenerateURILocation.generateURI(medicineSaved.id());

        return ResponseEntity.created(location).body(CustomMedicineItemDTO.fromMedicineResponseDTO(medicineSaved));
    }

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
                                                                            @RequestParam(value = "conclusion", required = false) String conclusion,
                                                                            @RequestParam(value = "fieldSort", required = false, defaultValue = "DAY_HOUR") FieldSortMedicineItem fieldSort,
                                                                            @RequestParam(value = "typeSort", required = false , defaultValue = "ASC") TypeSort typeSort) {

        return ResponseEntity.ok(medicineItemService.findAllWithCustomPage(name,actualPage,sizePage, initialDate,finalDate,conclusion,fieldSort,typeSort));
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
