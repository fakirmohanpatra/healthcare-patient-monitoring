package com.example.healthcare.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.healthcare.controller.dto.AppointmentResponse;
import com.example.healthcare.controller.dto.CreateAppointmentRequest;
import com.example.healthcare.service.AppointmentService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE') or hasRole('ROLE_ADMIN')")
    public AppointmentResponse scheduleAppointment(
            @RequestBody CreateAppointmentRequest request) {
        return appointmentService.scheduleAppointment(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE') or hasRole('ROLE_ADMIN')")
    public List<AppointmentResponse> getAppointmentsBetweenDates(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return appointmentService.getAppointmentsBetweenDates(startDate, endDate);
    }

    @GetMapping("/by-doctor/{doctorId}")
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE') or hasRole('ROLE_ADMIN')")
    public List<AppointmentResponse> getAppointmentsByDoctorId(
            @PathVariable String doctorId) {
        return appointmentService.getAppointmentsByDoctorId(doctorId);
    }

    @GetMapping("/by-patient/{patientId}")
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE') or hasRole('ROLE_ADMIN')")
    public List<AppointmentResponse> getAppointmentsByPatientId(
            @PathVariable String patientId) {
        return appointmentService.getAppointmentsByPatientId(patientId);
    }

    @GetMapping("/{appointmentId}")
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE') or hasRole('ROLE_ADMIN')")
    public AppointmentResponse getAppointmentById(
            @PathVariable String appointmentId) {
        return appointmentService.getAppointmentById(appointmentId);
    }
    
    
}
