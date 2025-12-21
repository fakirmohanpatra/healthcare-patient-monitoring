package com.example.healthcare.repository.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.healthcare.domain.vitals.PatientVital;
import com.example.healthcare.domain.vitals.VitalType;

public interface PatientVitalRepository extends MongoRepository<PatientVital, String> {
    List<PatientVital> findByPatientId(String patientId);
    List<PatientVital> findByPatientIdAndVitalType(String patientId, VitalType vitalType);
    
}
