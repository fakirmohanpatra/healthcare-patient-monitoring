package com.example.healthcare.repository.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.healthcare.model.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, UUID> {
    List<MedicalRecord> findByPatient_Id(UUID patientId);
    List<MedicalRecord> findByDoctorId(UUID doctorId);
}
