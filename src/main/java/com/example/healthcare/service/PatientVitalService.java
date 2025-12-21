package com.example.healthcare.service;

import java.util.List;

import com.example.healthcare.domain.vitals.PatientVital;
import com.example.healthcare.domain.vitals.VitalType;

public interface PatientVitalService {
    List<PatientVital> getVitalsByPatientId(String patientId);
    List<PatientVital> getVitalsByPatientIdAndType(String patientId, VitalType vitalType);

    PatientVital recordPatientVital(PatientVital patientVital);
    List<PatientVital> recordVitalsBulk(List<PatientVital> patientVitals);

}
