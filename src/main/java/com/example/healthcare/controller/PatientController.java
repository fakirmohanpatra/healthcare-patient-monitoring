package com.example.healthcare.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.healthcare.controller.dto.CreatePatientRequest;
import com.example.healthcare.controller.dto.PatientResponse;
import com.example.healthcare.service.PatientService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    
    private final PatientService patientService;
    
    @GetMapping("/{patientId}")
    public PatientResponse getPatientById(@PathVariable String patientId) {
        return patientService.getPatientById(patientId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE')")
    public PatientResponse createPatient(@RequestBody CreatePatientRequest request) {
        return patientService.createPatient(request);
    }

    @GetMapping
    public List<PatientResponse> getAllPatients() {
        return patientService.getAllPatients();
    }
    
}
