package io.github.cristian_eds.InfoMed.repository;

import io.github.cristian_eds.InfoMed.controller.dto.HistoricLogResponseDTO;
import io.github.cristian_eds.InfoMed.models.HistoricLog;
import io.github.cristian_eds.InfoMed.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HistoricLogRepository extends JpaRepository<HistoricLog, UUID> {

    Page<HistoricLogResponseDTO> findByUser(User user, Pageable pageable);
}
