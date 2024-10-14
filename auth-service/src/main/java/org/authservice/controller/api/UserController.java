package org.authservice.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.authservice.model.dto.UserCredentialsDto;
import org.authservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@Tag(name = "Users", description = "API for users management")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Find user by id",description = "Returns user by id")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserCredentialsDto> findUserById(@PathVariable Long id) {
        var user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Find all users", description = "Returns all users")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserCredentialsDto>> findAllUsers() {
        var users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @Operation(summary = "Delete user", description = "Deletes user by given id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update user", description = "Updates corresponding user record by given id")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserCredentialsDto> updateUser(@RequestBody @Valid UserCredentialsDto userDto,
                                                         @PathVariable Long id) {
        var updatedUser = userService.update(userDto, id);
        return ResponseEntity.ok(updatedUser);
    }
}
