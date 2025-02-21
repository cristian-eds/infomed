package io.github.cristian_eds.InfoMed.repository;

import io.github.cristian_eds.InfoMed.models.MedicineItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicineItemRepository extends JpaRepository<MedicineItem, UUID> {
}
