package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.controller.dto.HistoricLogResponseDTO;
import io.github.cristian_eds.InfoMed.service.HistoricLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class HistoricLogController {

    private final HistoricLogService historicLogService;

    @GetMapping
    public ResponseEntity<Page<HistoricLogResponseDTO>> getLogs(
            @RequestParam("actualPage") int actualPage,
            @RequestParam("sizePage") int sizePage) {
        return ResponseEntity.ok(historicLogService.findAllByUser(actualPage, sizePage));
    }
}
