package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.controller.dto.HistoricLogResponseDTO;
import io.github.cristian_eds.InfoMed.controller.dto.HistoricLogSaveDTO;
import io.github.cristian_eds.InfoMed.models.HistoricLog;
import io.github.cristian_eds.InfoMed.models.User;
import io.github.cristian_eds.InfoMed.repository.HistoricLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HistoricLogService {

    private final HistoricLogRepository historicLogRepository;
    private final SecurityService securityService;

    public HistoricLog save(HistoricLogSaveDTO historicLogSaveDTO) {
        HistoricLog historicLog = new HistoricLog();

        User user = securityService.getAuthenticatedUser();

        historicLog.setDateHour(LocalDateTime.now());
        historicLog.setAction(historicLogSaveDTO.action());
        historicLog.setDescription(historicLogSaveDTO.description());
        historicLog.setStatusCode(historicLogSaveDTO.statusCode());
        historicLog.setUser(user);

        return historicLogRepository.save(historicLog);
    }

    public Page<HistoricLogResponseDTO> findAllByUser(int actualPage, int sizePage) {
        User user = securityService.getAuthenticatedUser();
        Pageable pageable = PageRequest.of(actualPage,sizePage);
        return historicLogRepository.findByUserOrderByDateHourDesc(user,pageable);
    }
}
