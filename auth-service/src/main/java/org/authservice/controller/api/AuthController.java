package org.authservice.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.authservice.model.dto.*;
import org.authservice.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authorization", description = "API used to authorize users in system")
public class AuthController {
    private final AuthenticationService authenticationServiceImpl;

    @Operation(summary = "Create new user", description = "Creates new user record")
    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid UserCredentialsDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationServiceImpl.signUp(request));
    }

    @Operation(summary = "Get jwt key", description = "Returns jwt key for existing user by given credentials")
    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(authenticationServiceImpl.signIn(request));
    }
}
