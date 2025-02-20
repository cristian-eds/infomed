package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.controller.dto.UserDTO;
import io.github.cristian_eds.InfoMed.service.UserService;
import io.github.cristian_eds.InfoMed.models.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public ResponseEntity<User>  test() {
        User test = new User();
        test.setId(UUID.randomUUID());
        test.setPassword("test");
        test.setEmail("test");
        return ResponseEntity.ok(test);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid UserDTO userDTO) {
        userService.createUser(UserDTO.toEntity(userDTO));
        return ResponseEntity.ok().build();
    }

}
