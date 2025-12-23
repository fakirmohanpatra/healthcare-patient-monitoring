package com.example.healthcare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.healthcare.domain.vitals.PatientVital;
import com.example.healthcare.domain.vitals.VitalType;
import com.example.healthcare.repository.mongo.PatientVitalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientVitalServiceImpl implements PatientVitalService {

    private final PatientVitalRepository patientVitalRepository;

    @Override
    public List<PatientVital> getVitalsByPatientId(String patientId) {
        log.info("[DB_FETCH] Fetching all vitals for patientId={}", patientId);
        List<PatientVital> vitals = patientVitalRepository.findByPatientId(patientId);
        log.info("[DB_FETCH] Retrieved {} vitals for patientId={}", vitals.size(), patientId);
        return vitals;
    }

    @Override
    public List<PatientVital> getVitalsByPatientIdAndType(String patientId, VitalType vitalType) {
        log.info("[DB_FETCH] Fetching {} vitals for patientId={}", vitalType, patientId);
        List<PatientVital> vitals = patientVitalRepository.findByPatientIdAndVitalType(patientId, vitalType);
        log.info("[DB_FETCH] Retrieved {} {} vitals for patientId={}", vitals.size(), vitalType, patientId);
        return vitals;
    }

    @Override
    public PatientVital recordPatientVital(PatientVital patientVital) {
        log.info("[DB_CREATE] Recording vital - patientId={}, type={}, value={}",
                patientVital.getPatientId(), patientVital.getVitalType(), patientVital.getValue());
        PatientVital saved = patientVitalRepository.save(patientVital);
        log.info("[DB_CREATE] Vital recorded successfully with id={}", saved.getId());
        return saved;
    }

    @Override
    public List<PatientVital> recordVitalsBulk(List<PatientVital> patientVitals) {
        log.info("[DB_CREATE] Bulk recording {} vitals", patientVitals.size());
        List<PatientVital> saved = patientVitalRepository.saveAll(patientVitals);
        log.info("[DB_CREATE] Successfully recorded {} vitals", saved.size());
        return saved;
    }
}
