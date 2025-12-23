package com.example.healthcare.controller.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MedicalRecordResponse {
    private String id;
    private String patientId;
    private String doctorId;
    private String diagnosis;
    private String treatmentPlan;
    private Instant recordDate;
}
