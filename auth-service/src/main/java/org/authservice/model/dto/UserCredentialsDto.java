package org.authservice.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCredentialsDto {
    @NotBlank
    @Size(min = 5, max = 50)
    private String username;
    @Size(max = 255)
    @NotBlank
    private String password;
    @Size(min = 5, max = 255)
    @NotBlank
    @Email
    private String email;
}
