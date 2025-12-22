package com.example.healthcare.repository.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.healthcare.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByExternalId(String externalId);
}
