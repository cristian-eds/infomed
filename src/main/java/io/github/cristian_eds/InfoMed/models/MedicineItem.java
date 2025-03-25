package io.github.cristian_eds.InfoMed.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "medicine_item")
public class MedicineItem {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Integer medicineItemSequence;

    @Column(nullable = false)
    private LocalDateTime dayHour;

    @Column(nullable = false)
    private Boolean conclusion;

    @Column(nullable = true)
    private LocalDateTime conclusionDayHour;

    @ManyToOne
    @JoinColumn(name = "id_medicine")
    private Medicine medicine;
}
