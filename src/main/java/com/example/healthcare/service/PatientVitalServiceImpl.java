package com.example.healthcare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.healthcare.domain.vitals.PatientVital;
import com.example.healthcare.domain.vitals.VitalType;
import com.example.healthcare.repository.mongo.PatientVitalRepository;

@Service
public class PatientVitalServiceImpl implements PatientVitalService {

    private final PatientVitalRepository patientVitalRepository;
    
    public PatientVitalServiceImpl(PatientVitalRepository patientVitalRepository) {
        this.patientVitalRepository = patientVitalRepository;
    }

    @Override
    public List<PatientVital> getVitalsByPatientId(String patientId) {
        return patientVitalRepository.findByPatientId(patientId);
    }

    @Override
    public List<PatientVital> getVitalsByPatientIdAndType(String patientId, VitalType vitalType) {
        return patientVitalRepository.findByPatientIdAndVitalType(patientId, vitalType);
    }

    @Override
    public PatientVital recordPatientVital(PatientVital patientVital) {
        return patientVitalRepository.save(patientVital);
    }

    @Override
    public List<PatientVital> recordVitalsBulk(List<PatientVital> patientVitals) {
        return patientVitalRepository.saveAll(patientVitals);
    }
    
    
}
