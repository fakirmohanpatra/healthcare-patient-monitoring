package com.example.healthcare.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.healthcare.controller.dto.AppointmentResponse;
import com.example.healthcare.controller.dto.CreateAppointmentRequest;
import com.example.healthcare.model.Appointment;
import com.example.healthcare.model.AppointmentStatus;
import com.example.healthcare.model.Patient;
import com.example.healthcare.repository.jpa.AppointmentRepository;
import com.example.healthcare.repository.jpa.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    @Override
    public AppointmentResponse scheduleAppointment(CreateAppointmentRequest request) {
        log.info("[DB_CREATE] Scheduling appointment - patientId={}, doctorId={}, date={}",
                request.getPatientId(), request.getDoctorId(), request.getAppointmentDate());
        
        log.debug("[DB_FETCH] Fetching patient with id={}", request.getPatientId());
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + request.getPatientId()));
        
        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctorId(request.getDoctorId())
                .appointmentDatetime(request.getAppointmentDate())
                .status(AppointmentStatus.SCHEDULED)
                .notes(request.getNotes())
                .build();
        
        log.debug("[DB_CREATE] Saving appointment to database");
        Appointment savedAppointment = appointmentRepository.save(appointment);
        log.info("[DB_CREATE] Appointment created successfully with id={}", savedAppointment.getId());
        return mapToResponse(savedAppointment);
    }

    @Override
    public AppointmentResponse getAppointmentById(UUID appointmentId) {
        log.info("[DB_FETCH] Fetching appointment with id={}", appointmentId);
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));
        log.debug("[DB_FETCH] Successfully retrieved appointment");
        return mapToResponse(appointment);
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByPatientId(UUID patientId) {
        log.info("[DB_FETCH] Fetching appointments for patientId={}", patientId);
        List<Appointment> appointments = appointmentRepository.findByPatient_Id(patientId);
        log.info("[DB_FETCH] Retrieved {} appointments for patientId={}", appointments.size(), patientId);
        return appointments.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByDoctorId(UUID doctorId) {
        log.info("[DB_FETCH] Fetching appointments for doctorId={}", doctorId);
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        log.info("[DB_FETCH] Retrieved {} appointments for doctorId={}", appointments.size(), doctorId);
        return appointments.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> getAppointmentsBetweenDates(String startDate, String endDate) {
        log.info("[DB_FETCH] Fetching appointments between {} and {}", startDate, endDate);
        List<Appointment> appointments = appointmentRepository.findByAppointmentDatetimeBetween(
                Instant.parse(startDate), Instant.parse(endDate));
        log.info("[DB_FETCH] Retrieved {} appointments in date range", appointments.size());
        return appointments.stream()
                .map(this::mapToResponse)
                .toList();
    }


    private AppointmentResponse mapToResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId().toString())
                .patientId(appointment.getPatient().getId().toString())
                .doctorId(appointment.getDoctorId().toString())
                .appointmentDatetime(appointment.getAppointmentDatetime().toString())
                .status(appointment.getStatus())
                .build();
    }
}
