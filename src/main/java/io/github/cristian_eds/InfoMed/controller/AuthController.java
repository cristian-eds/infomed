package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.controller.dto.LoginDTO;
import io.github.cristian_eds.InfoMed.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO) {
        var token =  authService.login(loginDTO.email(), loginDTO.password());
        return ResponseEntity.ok(token);
    }
}
