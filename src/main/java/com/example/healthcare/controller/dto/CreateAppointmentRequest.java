package com.example.healthcare.controller.dto;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateAppointmentRequest {
    
    @NotNull
    private UUID patientId;

    @NotNull
    private UUID doctorId;

    @NotNull
    private Instant appointmentDate;
    
    private String notes;
}
