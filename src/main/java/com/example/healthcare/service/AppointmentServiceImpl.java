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
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + request.getPatientId()));
        
        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctorId(request.getDoctorId())
                .appointmentDatetime(request.getAppointmentDate())
                .status(AppointmentStatus.SCHEDULED)
                .notes(request.getNotes())
                .build();
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return mapToResponse(savedAppointment);
    }

    @Override
    public AppointmentResponse getAppointmentById(String appointmentId) {
        Appointment appointment = appointmentRepository.findById(UUID.fromString(appointmentId))
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));
        return mapToResponse(appointment);
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByPatientId(String patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatient_Id(UUID.fromString(patientId));
        return appointments.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByDoctorId(String doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(UUID.fromString(doctorId));
        return appointments.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> getAppointmentsBetweenDates(String startDate, String endDate) {
        List<Appointment> appointments = appointmentRepository.findByAppointmentDatetimeBetween(
                Instant.parse(startDate), Instant.parse(endDate));
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
