package io.github.cristian_eds.InfoMed.models;

import io.github.cristian_eds.InfoMed.models.enums.ActionType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class HistoricLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ActionType action;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false)
    private LocalDateTime dateHour;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
}
