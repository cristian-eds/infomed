package io.github.cristian_eds.InfoMed.repository;

import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.models.MedicineItem;
import io.github.cristian_eds.InfoMed.models.Person;
import io.github.cristian_eds.InfoMed.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MedicineItemRepository extends JpaRepository<MedicineItem, UUID> {

    @Query("SELECT mi FROM MedicineItem mi JOIN mi.medicine m JOIN m.person p WHERE (p.accessCode = :accessCode OR m.user = :user) AND mi.dayHour >= CURRENT_TIMESTAMP AND  mi.conclusion = false ORDER BY mi.dayHour ASC LIMIT 1")
    Optional<MedicineItem> findFirstByDayHourGreaterThanEqualNow(@Param("accessCode") String accessCode, @Param("user") User user);

    @Query("SELECT mi FROM MedicineItem mi JOIN mi.medicine m WHERE mi.dayHour >= CURRENT_TIMESTAMP AND m.user = :user AND mi.conclusion = false AND m.person = :person ORDER BY mi.dayHour ASC LIMIT 1")
    Optional<MedicineItem> findFirstByDayHourGreaterThanEqualNowByPerson(@Param("user") User user, @Param("person") Person person);

    List<MedicineItem> findByMedicineOrderByMedicineItemSequence(Medicine medicine);

    @Query("SELECT mi FROM MedicineItem mi " +
            "JOIN mi.medicine m " +
            "WHERE mi.dayHour >= CURRENT_TIMESTAMP AND m.user = :user " +
            "AND mi.conclusion = false AND m.person = :person " +
            "AND mi.medicine = :medicine "+
            "ORDER BY mi.dayHour ASC LIMIT 1")
    Optional<MedicineItem> findNextPendingForMedicine(@Param("user") User user, @Param("person") Person person,@Param("medicine") Medicine medicine);
}
