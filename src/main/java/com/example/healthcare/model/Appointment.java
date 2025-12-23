package com.example.healthcare.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private UUID doctorId; // reference to User (ROLE_DOCTOR)

    @Column(nullable = false)
    private Instant appointmentDatetime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private String notes;
}
