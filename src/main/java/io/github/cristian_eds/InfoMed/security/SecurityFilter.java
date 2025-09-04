package io.github.cristian_eds.InfoMed.security;

import io.github.cristian_eds.InfoMed.models.User;
import io.github.cristian_eds.InfoMed.repository.UserRepository;
import io.github.cristian_eds.InfoMed.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoveryToken(request);
        if(token != null) {
            String accessCode = jwtTokenService.validateClaimAccessCodeFromToken(token);
            if(!accessCode.isEmpty()) {
                authenticateTokenFromAccessCode(accessCode);
            } else {
                String email = jwtTokenService.validateToken(token);
                authenticateNormalToken(email);
            }
        }
        filterChain.doFilter(request,response);
    }

    private String recoveryToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;

        return authHeader.replace("Bearer ", "");
    }

    private void authenticateNormalToken(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if(user != null){
            var authentication = new UsernamePasswordAuthenticationToken(user,null,List.of(new SimpleGrantedAuthority(user.getRole().toString())));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private void authenticateTokenFromAccessCode(String acessCode) {

    }
}
