package com.example.healthcare.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.healthcare.controller.dto.CreateMedicalRecordRequest;
import com.example.healthcare.controller.dto.MedicalRecordResponse;
import com.example.healthcare.service.MedicalRecordService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {
    
    private final MedicalRecordService medicalRecordService;
    
    @GetMapping("/{recordId}")
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE') or hasRole('ROLE_ADMIN') or hasRole('ROLE_PATIENT')")
    public MedicalRecordResponse getMedicalRecordById(@PathVariable String recordId) {
        return medicalRecordService.getMedicalRecordById(recordId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public MedicalRecordResponse createMedicalRecord(
            @RequestBody CreateMedicalRecordRequest request,
            Authentication authentication) {
        // Only doctors can create records - extract their ID from JWT
        String doctorId = authentication.getName(); // or extract from custom claims
        return medicalRecordService.createMedicalRecord(request, UUID.fromString(doctorId));
    }

    @GetMapping("/by-patient/{patientId}")
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE') or hasRole('ROLE_ADMIN') or hasRole('ROLE_PATIENT')")
    public List<MedicalRecordResponse> getMedicalRecordsByPatientId(@PathVariable String patientId) {
        return medicalRecordService.getMedicalRecordsByPatientId(patientId);
    }
}
