package io.github.cristian_eds.InfoMed.repository;

import io.github.cristian_eds.InfoMed.models.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface MedicineRepository extends JpaRepository<Medicine, UUID>, JpaSpecificationExecutor<Medicine> {

    List<Medicine> findByNameContainingIgnoreCase(String name);
}
