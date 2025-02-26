package io.github.cristian_eds.InfoMed.repository;

import io.github.cristian_eds.InfoMed.models.HistoricLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HistoricLogRepository extends JpaRepository<HistoricLog, UUID> {
}
