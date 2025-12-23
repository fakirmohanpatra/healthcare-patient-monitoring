package com.example.healthcare.service;

import java.util.List;
import java.util.UUID;

import com.example.healthcare.controller.dto.CreateMedicalRecordRequest;
import com.example.healthcare.controller.dto.MedicalRecordResponse;

public interface MedicalRecordService {
    MedicalRecordResponse getMedicalRecordById(UUID recordId);
    MedicalRecordResponse createMedicalRecord(CreateMedicalRecordRequest request, UUID doctorId);
    List<MedicalRecordResponse> getMedicalRecordsByPatientId(UUID patientId);
}
