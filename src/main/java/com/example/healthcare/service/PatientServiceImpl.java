package com.example.healthcare.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.healthcare.controller.dto.CreatePatientRequest;
import com.example.healthcare.controller.dto.PatientResponse;
import com.example.healthcare.model.Patient;
import com.example.healthcare.repository.jpa.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PatientServiceImpl implements PatientService {
    
    private final PatientRepository patientRepository;
    
    @Override
    public PatientResponse createPatient(CreatePatientRequest request) {
        log.info("Creating patient with externalId={}", request.getExternalId());

        Patient patient = Patient.builder()
                .name(request.getName())
                .age(request.getAge())
                .gender(request.getGender())
                .externalId(request.getExternalId())
                .build();
        Patient savedPatient = patientRepository.save(patient);
        log.info("Patient created with id={}", savedPatient.getId());
        return mapToResponse(savedPatient);
    }

    @Override
    public PatientResponse getPatientById(String patientId) {
        log.info("Fetching patient with id={}", patientId);
        Patient patient = patientRepository.findById(UUID.fromString(patientId))
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));
        return mapToResponse(patient);
    }

    @Override
    public List<PatientResponse> getAllPatients() {
        log.info("Fetching all patients");
        List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private PatientResponse mapToResponse(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId().toString())
                .name(patient.getName())
                .age(patient.getAge())
                .gender(patient.getGender())
                .externalId(patient.getExternalId())
                .build();
    }
}
