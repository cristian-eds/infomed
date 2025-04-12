package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.controller.dto.ChangeUserPasswordDTO;
import io.github.cristian_eds.InfoMed.controller.dto.UserUpdateDTO;
import io.github.cristian_eds.InfoMed.exception.custom.EmailAlreadyExistsException;
import io.github.cristian_eds.InfoMed.exception.custom.IncorrectPasswordException;
import io.github.cristian_eds.InfoMed.models.User;
import io.github.cristian_eds.InfoMed.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        Optional<User> userFound = findByEmail(user.getEmail());
        if(userFound.isPresent()) throw new EmailAlreadyExistsException("There is already a user with this email address.");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User with id not found."));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void changePassword(UUID uuid, ChangeUserPasswordDTO changeUserPasswordDTO) {
        User userFound = findById(uuid);
        System.out.println("Passou aqui");
        if(passwordEncoder.matches(changeUserPasswordDTO.currentPassword(),userFound.getPassword())) {
            System.out.println("Senhas conferem");
            userFound.setPassword(passwordEncoder.encode(changeUserPasswordDTO.newPassword()));
        } else {
            throw new IncorrectPasswordException("Incorrect password. Check!");
        }
        userRepository.save(userFound);
    }

    public User updateUser(UUID uuid, @Valid UserUpdateDTO userUpdateDTO) {
        User userFound = findById(uuid);
        userFound.setName(userUpdateDTO.name());
        userFound.setEmail(userUpdateDTO.email());
        return userRepository.save(userFound);
    }
}
