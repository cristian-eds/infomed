package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    @GetMapping("/test")
    public ResponseEntity<User>  test() {
        User test = new User();
        test.setId(UUID.randomUUID());
        test.setPassword("test");
        test.setEmail("test");
        return ResponseEntity.ok(test);
    }
}
