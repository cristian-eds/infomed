package io.github.cristian_eds.InfoMed.repository;

import io.github.cristian_eds.InfoMed.models.Person;
import io.github.cristian_eds.InfoMed.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {

    List<Person> findByUserFather(User user);

    Optional<Person> findByUserFatherAndId(User user, UUID id);
}
