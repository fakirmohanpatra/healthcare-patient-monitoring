package com.example.healthcare.domain.vitals;

import java.time.Instant;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.*;

@Document(collection = "patient_vitals")
@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientVital {
    
    @Id 
    private String id;
    
    private String patientId;
    private VitalType vitalType;
    private double value;
    private Instant recordedAt;
}
