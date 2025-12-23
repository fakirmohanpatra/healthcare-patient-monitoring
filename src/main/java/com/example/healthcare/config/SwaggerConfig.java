package com.example.healthcare.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.info.Contact;

@Configuration
@OpenAPIDefinition(
    info = @io.swagger.v3.oas.annotations.info.Info(
        title = "Healthcare Patient Monitoring & Analytics API",
        version = "1.0.0",
        description = """
            Production-ready healthcare data platform API for real-time patient monitoring, clinical event tracking, 
            and secure access to medical records.
            
            ### Authentication:
            1. Call POST /api/auth/login with credentials (username: 'admin', password: 'adminpass')
            2. Copy the JWT token from the response
            3. Click 'Authorize' button (🔒) at the top
            4. Enter: Bearer {your-token}
            5. Click 'Authorize' to use authenticated endpoints
            
            ### Available Roles:
            - ROLE_ADMIN: Full access
            - ROLE_DOCTOR: Create medical records, schedule appointments
            - ROLE_NURSE: Schedule appointments, view records
            - ROLE_PATIENT: View own records and appointments
            """,
        contact = @Contact(
            name = "Healthcare API Support",
            email = "support@healthcare.com"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "Local Development Server")
    },
    security = @SecurityRequirement(name = "bearerAuth")
)

@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "Enter JWT token obtained from /api/auth/login endpoint"
)

public class SwaggerConfig {
    
}
