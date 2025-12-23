package com.example.healthcare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.healthcare.controller.dto.ErrorResponse;
import com.example.healthcare.controller.dto.LoginRequest;
import com.example.healthcare.controller.dto.LoginResponse;
import com.example.healthcare.utils.security.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Authentication API - Login to get JWT token")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    
    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    @PostMapping("/login")
    @Operation(
        summary = "Authenticate user and get JWT token",
        description = "Login with username and password to receive a JWT token for API authentication. Use this token in the Authorization header as 'Bearer {token}' for subsequent requests."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Login successful - JWT token generated",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = LoginResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Invalid credentials",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            log.info("[AUTH] Login attempt for username: {}", request.getUsername());
            
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
                )
            );

            String token = jwtUtil.generateToken(authentication.getName());
            
            log.info("[AUTH] Login successful for username: {}", request.getUsername());

            LoginResponse response = LoginResponse.builder()
                .token(token)
                .type("Bearer")
                .username(authentication.getName())
                .expiresIn(jwtExpirationMs)
                .build();

            return ResponseEntity.ok(response);
            
        } catch (BadCredentialsException e) {
            log.warn("[AUTH] Failed login attempt for username: {} - Invalid credentials", request.getUsername());
            
            ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Unauthorized")
                .message("Invalid username or password")
                .status(HttpStatus.UNAUTHORIZED.value())
                .build();
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            
        } catch (Exception e) {
            log.error("[AUTH] Authentication error for username: {} - {}", request.getUsername(), e.getMessage(), e);
            
            ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Internal Server Error")
                .message("An error occurred during authentication")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}