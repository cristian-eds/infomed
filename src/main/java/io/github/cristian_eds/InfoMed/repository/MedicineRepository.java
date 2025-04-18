package io.github.cristian_eds.InfoMed.repository;

import io.github.cristian_eds.InfoMed.controller.dto.CustomMedicineItemDTO;
import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MedicineRepository extends JpaRepository<Medicine, UUID>, JpaSpecificationExecutor<Medicine> {

    List<Medicine> findByNameContainingIgnoreCase(String name);

    @Query("SELECT new io.github.cristian_eds.InfoMed.controller.dto.CustomMedicineItemDTO(m.id, mi.id, m.name, mi.medicineItemSequence, SIZE(m.medicineItems), m.frequencyHours, mi.dayHour, mi.conclusion, mi.conclusionDayHour) "+
    " FROM Medicine m JOIN m.medicineItems mi " +
            "WHERE UPPER(m.name) LIKE %:name% AND m.user = :user " +
            "ORDER BY mi.dayHour")
    Page<CustomMedicineItemDTO> findCustomMedicineItemsWithPagination(
            @Param("name") String name,
            @Param("user") User user,
            org.springframework.data.domain.Pageable pageable);

    @Query("SELECT new io.github.cristian_eds.InfoMed.controller.dto.CustomMedicineItemDTO(m.id, mi.id, m.name, mi.medicineItemSequence, SIZE(m.medicineItems), m.frequencyHours, mi.dayHour, mi.conclusion, mi.conclusionDayHour) "+
            " FROM Medicine m JOIN m.medicineItems mi " +
            "WHERE UPPER(m.name) LIKE %:name% AND m.user = :user AND mi.dayHour BETWEEN :startDateTime AND :finalDateTime " +
            "ORDER BY mi.dayHour")
    Page<CustomMedicineItemDTO> findCustomMedicineItemsWithPagination(
            @Param("name") String name,
            @Param("user") User user,
            @Param("startDateTime")LocalDateTime startDateTime,
            @Param("finalDateTime") LocalDateTime finalDateTime,
            org.springframework.data.domain.Pageable pageable);

    @Query("SELECT new io.github.cristian_eds.InfoMed.controller.dto.CustomMedicineItemDTO(m.id, mi.id, m.name, mi.medicineItemSequence, SIZE(m.medicineItems), m.frequencyHours, mi.dayHour, mi.conclusion, mi.conclusionDayHour) "+
            " FROM Medicine m JOIN m.medicineItems mi " +
            "WHERE UPPER(m.name) LIKE %:name% AND m.user = :user AND mi.conclusion = :conclusion " +
            "ORDER BY mi.dayHour")
    Page<CustomMedicineItemDTO> findCustomMedicineItemsWithPagination(
            @Param("name") String name,
            @Param("user") User user,
            @Param("conclusion") boolean conclusion,
            org.springframework.data.domain.Pageable pageable);

    @Query("SELECT new io.github.cristian_eds.InfoMed.controller.dto.CustomMedicineItemDTO(m.id, mi.id, m.name, mi.medicineItemSequence, SIZE(m.medicineItems), m.frequencyHours, mi.dayHour, mi.conclusion, mi.conclusionDayHour) "+
            " FROM Medicine m JOIN m.medicineItems mi " +
            "WHERE UPPER(m.name) LIKE %:name% AND m.user = :user AND mi.conclusion = :conclusion AND mi.dayHour BETWEEN :startDateTime AND :finalDateTime " +
            "ORDER BY mi.dayHour")
    Page<CustomMedicineItemDTO> findCustomMedicineItemsWithPagination(
            @Param("name") String name,
            @Param("user") User user,
            @Param("conclusion") boolean conclusion,
            @Param("startDateTime")LocalDateTime startDateTime,
            @Param("finalDateTime") LocalDateTime finalDateTime,
            org.springframework.data.domain.Pageable pageable);


}
