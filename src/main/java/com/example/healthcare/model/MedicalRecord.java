package com.example.healthcare.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medical_records")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalRecord {
    
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private UUID doctorId; // reference to User (ROLE_DOCTOR)

    @Column(nullable = false)
    private String diagnosis;

    @Column(length = 2000)
    private String treatmentPlan;

    @Column(nullable = false)
    private Instant recordDate;
}
