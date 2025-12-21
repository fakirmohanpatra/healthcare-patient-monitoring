package com.example.healthcare.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.healthcare.domain.vitals.PatientVital;
import com.example.healthcare.domain.vitals.VitalType;
import com.example.healthcare.service.PatientVitalService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/patient-vitals")
public class PatientVitalController {

    private final PatientVitalService patientVitalService;

    public PatientVitalController(PatientVitalService patientVitalService) {
        this.patientVitalService = patientVitalService;
    }

    // Get all vitals for a patient
    @GetMapping("/{patientId}")
    public ResponseEntity<List<PatientVital>> getVitals(@PathVariable String patientId) {
        List<PatientVital> vitals = patientVitalService.getVitalsByPatientId(patientId);

        return ResponseEntity.ok(vitals);
    }

    // Get vitals of a specific type for a patient
    @GetMapping("/{patientId}/{vitalType}")
    public ResponseEntity<List<PatientVital>> getVitalsByType(
                @PathVariable String patientId,
                @PathVariable VitalType vitalType) {
        
            List<PatientVital> vitals = patientVitalService.getVitalsByPatientIdAndType(patientId, vitalType);
            return ResponseEntity.ok(vitals);
    }

    // Add a new vital
    @PostMapping
    public ResponseEntity<PatientVital> addVital(@RequestBody PatientVital patientVital) {
        PatientVital vital = patientVitalService.recordPatientVital(patientVital);

        return ResponseEntity.ok(vital);
    }

    // Bulk add vitals
    @PostMapping("/bulk")
    public ResponseEntity<List<PatientVital>> addVitals(@RequestBody List<PatientVital> patientVitals) {
        List<PatientVital> vitals = patientVitalService.recordVitalsBulk(patientVitals);
        return ResponseEntity.ok(vitals);
    }
    
    
    

    
    
    
}
