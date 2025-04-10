package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.controller.dto.ChangeUserPasswordDTO;
import io.github.cristian_eds.InfoMed.exception.custom.EmailAlreadyExistsException;
import io.github.cristian_eds.InfoMed.exception.custom.IncorrectPasswordException;
import io.github.cristian_eds.InfoMed.models.User;
import io.github.cristian_eds.InfoMed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        User userFound = findByEmail(user.getEmail());
        if(userFound != null) throw new EmailAlreadyExistsException("There is already a user with this email address.");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User with id not found."));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("User with e-mail not found"));
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

}
