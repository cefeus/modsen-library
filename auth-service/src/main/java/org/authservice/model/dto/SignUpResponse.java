package org.authservice.model.dto;

public record SignUpResponse(
        Long id,
        String username,
        String password,
        String email
) {
}
