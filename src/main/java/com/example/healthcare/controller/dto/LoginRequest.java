package com.example.healthcare.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Login credentials")
public class LoginRequest {
    
    @NotBlank(message = "Username is required")
    @Schema(description = "Username for authentication", example = "admin", required = true)
    private String username;
    
    @NotBlank(message = "Password is required")
    @Schema(description = "Password for authentication", example = "adminpass", required = true)
    private String password;
}
