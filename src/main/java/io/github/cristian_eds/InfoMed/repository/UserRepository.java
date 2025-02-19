package io.github.cristian_eds.InfoMed.repository;

import io.github.cristian_eds.InfoMed.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
