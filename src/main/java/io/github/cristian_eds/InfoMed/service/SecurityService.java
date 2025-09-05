package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.models.User;
import io.github.cristian_eds.InfoMed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    @Value("${api.guest.email}")
    private String GUEST_EMAIL;

    private final UserRepository userRepository;

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            User guest = userRepository.findByEmail(GUEST_EMAIL).orElse(null);
            guest.setName(userDetails.getUsername());
            return guest;
        }
        return (User) authentication.getPrincipal();
    }
}
