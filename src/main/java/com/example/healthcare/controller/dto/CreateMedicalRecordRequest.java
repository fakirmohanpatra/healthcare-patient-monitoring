package com.example.healthcare.controller.dto;

import java.util.UUID;

import com.mongodb.lang.NonNull;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateMedicalRecordRequest {

    @NonNull
    private UUID patientId;

    @NotBlank
    private String diagnosis;

    @Size(max = 1000)
    private String treatmentPlan;
}
