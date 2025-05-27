package io.github.cristian_eds.InfoMed.repository;

import io.github.cristian_eds.InfoMed.models.Person;
import io.github.cristian_eds.InfoMed.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {

    Page<Person> findByUserFather(User user, Pageable pageable);

    Optional<Person> findByUserFatherAndId(User user, UUID id);
}
