package io.github.cristian_eds.InfoMed.repository;

import io.github.cristian_eds.InfoMed.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
}
