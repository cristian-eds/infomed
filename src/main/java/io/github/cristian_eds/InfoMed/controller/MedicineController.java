package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.controller.common.GenerateURILocation;
import io.github.cristian_eds.InfoMed.controller.dto.CreateMedicineDTO;
import io.github.cristian_eds.InfoMed.controller.dto.CustomMedicineItemDTO;
import io.github.cristian_eds.InfoMed.controller.dto.MedicineResponseDTO;
import io.github.cristian_eds.InfoMed.controller.dto.MedicineUpdateDTO;
import io.github.cristian_eds.InfoMed.models.Medicine;
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
@RequestMapping("/medicine")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @PostMapping
    public ResponseEntity<List<CustomMedicineItemDTO>> create(@RequestBody @Valid CreateMedicineDTO createMedicineDTO) {
        LocalDateTime initialLocalDateTime = LocalDateTime.parse(createMedicineDTO.initialDateTime());

        Medicine medicine = CreateMedicineDTO.toEntity(createMedicineDTO);
        MedicineResponseDTO medicineSaved = MedicineResponseDTO.fromEntity(medicineService.create(medicine,initialLocalDateTime));

        URI location = GenerateURILocation.generateURI(medicineSaved.id());

        return ResponseEntity.created(location).body(CustomMedicineItemDTO.fromMedicineResponseDTO(medicineSaved));
    }

    @GetMapping
    public ResponseEntity<Page<MedicineResponseDTO>> getAll(@RequestParam(value = "name", required = false) String name,
                                                              @RequestParam(value = "actualPage", required = false, defaultValue = "0") int actualPage,
                                                              @RequestParam(value = "sizePage", required = false, defaultValue = "6") int sizePage) {
        return ResponseEntity.ok(medicineService.findAll(name,actualPage,sizePage));
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

    @PutMapping("{id}")
    public ResponseEntity<MedicineResponseDTO> updateMedicine(@PathVariable("id") String id, @RequestBody MedicineUpdateDTO medicineUpdateDTO) {
        UUID uuid = UUID.fromString(id);
        MedicineResponseDTO medicineResponseDTO = MedicineResponseDTO.fromEntity(medicineService.update(uuid, medicineUpdateDTO));
        return ResponseEntity.ok(medicineResponseDTO);
    }
}
