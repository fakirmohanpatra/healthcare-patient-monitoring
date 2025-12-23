package com.example.healthcare.controller.dto;

import com.example.healthcare.domain.vitals.VitalType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatientVitalRequest {
    
    @NotBlank
    private String patientId;
    
    @NotNull
    private VitalType vitalType;
    
    @NotNull
    private Double value;
}
