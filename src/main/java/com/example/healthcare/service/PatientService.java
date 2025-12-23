package com.example.healthcare.service;

import java.util.List;
import java.util.UUID;

import com.example.healthcare.controller.dto.CreatePatientRequest;
import com.example.healthcare.controller.dto.PatientResponse;

public interface PatientService {
    PatientResponse getPatientById(UUID patientId);
    PatientResponse createPatient(CreatePatientRequest request);
    List<PatientResponse> getAllPatients();
}
