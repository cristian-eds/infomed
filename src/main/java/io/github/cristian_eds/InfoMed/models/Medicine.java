package io.github.cristian_eds.InfoMed.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false)
    private Double totalDays;

    @Column(nullable = false)
    private Double frequencyHours;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @CreatedDate
    private LocalDateTime registrationDate;

    @LastModifiedDate
    private LocalDateTime atualizationDate;

}
