package com.example.healthcare.bootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.healthcare.domain.vitals.PatientVital;
import com.example.healthcare.domain.vitals.VitalType;
import com.example.healthcare.repository.mongo.PatientVitalRepository;

@Component
public class MongoDataSeeder implements CommandLineRunner {

    private final PatientVitalRepository patientVitalRepository;
    private final Random random = new Random();

    public MongoDataSeeder(PatientVitalRepository patientVitalRepository) {
        this.patientVitalRepository = patientVitalRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        
        if(patientVitalRepository.count() >= 500) {
            return;
        }

        List<PatientVital> vitals = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            String patientId = "patient" + ((i % 10) + 1); // 10 unique patients

            vitals.add(createVital(patientId, VitalType.HEART_RATE, 60 + random.nextDouble() * 40));
            vitals.add(createVital(patientId, VitalType.SPO2, 90 + random.nextDouble() * 10));
            vitals.add(createVital(patientId, VitalType.BP_SYS, 100 + random.nextDouble() * 40));
            vitals.add(createVital(patientId, VitalType.BP_DIA, 60 + random.nextDouble() * 20));
        }

        patientVitalRepository.saveAll(vitals);
        System.out.println("Seeded " + vitals.size() + " patient vitals into MongoDB.");
    }

    private PatientVital createVital(String patientId, VitalType type, double value) {
        return PatientVital.builder()
                .patientId(patientId)
                .vitalType(type)
                .value(value)
                .recordedAt(java.time.Instant.now().minusSeconds(random.nextInt(86400))) // within last 24 hours
                .build();
    }
    
}
