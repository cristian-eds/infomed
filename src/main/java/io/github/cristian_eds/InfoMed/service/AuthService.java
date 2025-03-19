package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.controller.dto.TokenDTO;
import io.github.cristian_eds.InfoMed.exception.custom.InvalidLoginException;
import io.github.cristian_eds.InfoMed.models.User;
import io.github.cristian_eds.InfoMed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    public TokenDTO login(String email, String password) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) throw new UsernameNotFoundException("User with email not found");

        if (checkPasswordsMatches(password, user.getPassword())){
            var authUsernamePassword = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            return jwtTokenService.generateToken(email);
        } else {
            throw new InvalidLoginException("Incorrect password.");
        }
    }

    private boolean checkPasswordsMatches(String providedPassword, String encryptedPassword) {
        return passwordEncoder.matches(providedPassword, encryptedPassword);
    }
}
