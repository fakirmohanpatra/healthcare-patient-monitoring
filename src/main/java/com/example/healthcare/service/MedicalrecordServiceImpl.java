package com.example.healthcare.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.healthcare.controller.dto.CreateMedicalRecordRequest;
import com.example.healthcare.controller.dto.MedicalRecordResponse;
import com.example.healthcare.model.MedicalRecord;
import com.example.healthcare.model.Patient;
import com.example.healthcare.repository.jpa.MedicalRecordRepository;
import com.example.healthcare.repository.jpa.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MedicalrecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;

    @Override
    public MedicalRecordResponse getMedicalRecordById(String recordId) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(UUID.fromString(recordId))
                .orElseThrow(() -> new RuntimeException("Medical record not found with id: " + recordId));
        return mapToResponse(medicalRecord);
    }

    @Override
    public MedicalRecordResponse createMedicalRecord(CreateMedicalRecordRequest request, UUID doctorId) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + request.getPatientId()));
        
        MedicalRecord medicalRecord = MedicalRecord.builder()
                .patient(patient)
                .doctorId(doctorId)
                .diagnosis(request.getDiagnosis())
                .treatmentPlan(request.getTreatmentPlan())
                .recordDate(Instant.now())
                .build();
        MedicalRecord savedRecord = medicalRecordRepository.save(medicalRecord);
        return mapToResponse(savedRecord);
    }

    @Override
    public List<MedicalRecordResponse> getMedicalRecordsByPatientId(String patientId) {
        List<MedicalRecord> records = medicalRecordRepository.findByPatient_Id(UUID.fromString(patientId));
        return records.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private MedicalRecordResponse mapToResponse(MedicalRecord medicalRecord) {
        return MedicalRecordResponse.builder()
                .id(medicalRecord.getId().toString())
                .patientId(medicalRecord.getPatient().getId().toString())
                .doctorId(medicalRecord.getDoctorId().toString())
                .diagnosis(medicalRecord.getDiagnosis())
                .treatmentPlan(medicalRecord.getTreatmentPlan())
                .recordDate(medicalRecord.getRecordDate())
                .build();
    }
    
}
