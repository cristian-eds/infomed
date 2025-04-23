package io.github.cristian_eds.InfoMed.repository;

import io.github.cristian_eds.InfoMed.models.MedicineItem;
import io.github.cristian_eds.InfoMed.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MedicineItemRepository extends JpaRepository<MedicineItem, UUID> {

    @Query("SELECT mi FROM MedicineItem mi JOIN mi.medicine m  WHERE mi.dayHour >= CURRENT_TIMESTAMP AND m.user = :user ORDER BY mi.dayHour ASC LIMIT 1")
    Optional<MedicineItem> findFirstByDayHourGreaterThanEqualNow(@Param("user") User user);
}
