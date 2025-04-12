package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.controller.dto.ChangeUserPasswordDTO;
import io.github.cristian_eds.InfoMed.controller.dto.UserDTO;
import io.github.cristian_eds.InfoMed.controller.dto.UserResponseDTO;
import io.github.cristian_eds.InfoMed.controller.dto.UserUpdateDTO;
import io.github.cristian_eds.InfoMed.service.UserService;
import io.github.cristian_eds.InfoMed.models.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserDTO userDTO) {
        UserResponseDTO userResponseDTO = UserResponseDTO.fromEntity(userService.createUser(UserDTO.toEntity(userDTO)));
        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping
    public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam("email") String email) {
        UserResponseDTO userResponseDTO = UserResponseDTO.fromEntity(
                userService.findByEmail(email)
                        .orElseThrow(() ->
                                new NoSuchElementException("No user found with this email")));
        return ResponseEntity.ok(userResponseDTO);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> changePassword(@PathVariable String id, @RequestBody @Valid ChangeUserPasswordDTO changeUserPasswordDTO) {
        UUID uuid = UUID.fromString(id);
        userService.changePassword(uuid,changeUserPasswordDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String id, @RequestBody @Valid UserUpdateDTO userUpdateDTO) {
        UUID uuid = UUID.fromString(id);
        User userUpdated = userService.updateUser(uuid,userUpdateDTO);
        return ResponseEntity.ok(UserResponseDTO.fromEntity(userUpdated));
    }

}
