package org.authservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignInRequest(
        @Size(min = 5, max = 50)
        @NotBlank
        String username,
        @Size(max = 255)
        String password
) {
}
