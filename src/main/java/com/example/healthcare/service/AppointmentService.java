package com.example.healthcare.service;

import java.util.List;
import java.util.UUID;
import com.example.healthcare.controller.dto.AppointmentResponse;
import com.example.healthcare.controller.dto.CreateAppointmentRequest;

public interface AppointmentService {
    AppointmentResponse scheduleAppointment(CreateAppointmentRequest request);
    AppointmentResponse getAppointmentById(UUID appointmentId);
    List<AppointmentResponse> getAppointmentsByPatientId(UUID patientId);
    List<AppointmentResponse> getAppointmentsByDoctorId(UUID doctorId);
    List<AppointmentResponse> getAppointmentsBetweenDates(String startDate, String endDate);

}
