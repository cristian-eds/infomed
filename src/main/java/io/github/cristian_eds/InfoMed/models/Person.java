package io.github.cristian_eds.InfoMed.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(name = "access_code")
    private String accessCode;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String phone;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "id_user_father")
    private User userFather;

    @OneToMany(mappedBy = "person")
    private List<Medicine> medicines;

}
