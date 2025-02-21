package io.github.cristian_eds.InfoMed.repository;

import io.github.cristian_eds.InfoMed.models.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicineRepository extends JpaRepository<Medicine, UUID> {
}
