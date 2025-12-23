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
    public MedicalRecordResponse getMedicalRecordById(UUID recordId) {
        log.info("[DB_FETCH] Fetching medical record with id={}", recordId);
        MedicalRecord medicalRecord = medicalRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Medical record not found with id: " + recordId));
        log.debug("[DB_FETCH] Successfully retrieved medical record");
        return mapToResponse(medicalRecord);
    }

    @Override
    public MedicalRecordResponse createMedicalRecord(CreateMedicalRecordRequest request, UUID doctorId) {
        log.info("[DB_CREATE] Creating medical record - patientId={}, doctorId={}, diagnosis={}",
                request.getPatientId(), doctorId, request.getDiagnosis());
        
        log.debug("[DB_FETCH] Fetching patient with id={}", request.getPatientId());
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + request.getPatientId()));
        
        MedicalRecord medicalRecord = MedicalRecord.builder()
                .patient(patient)
                .doctorId(doctorId)
                .diagnosis(request.getDiagnosis())
                .treatmentPlan(request.getTreatmentPlan())
                .recordDate(Instant.now())
                .build();
        
        log.debug("[DB_CREATE] Saving medical record to database");
        MedicalRecord savedRecord = medicalRecordRepository.save(medicalRecord);
        log.info("[DB_CREATE] Medical record created successfully with id={}", savedRecord.getId());
        return mapToResponse(savedRecord);
    }

    @Override
    public List<MedicalRecordResponse> getMedicalRecordsByPatientId(UUID patientId) {
        log.info("[DB_FETCH] Fetching medical records for patientId={}", patientId);
        List<MedicalRecord> records = medicalRecordRepository.findByPatient_Id(patientId);
        log.info("[DB_FETCH] Retrieved {} medical records for patientId={}", records.size(), patientId);
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
