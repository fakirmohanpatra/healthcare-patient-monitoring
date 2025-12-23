package com.example.healthcare.service;

import java.util.List;
import com.example.healthcare.controller.dto.AppointmentResponse;
import com.example.healthcare.controller.dto.CreateAppointmentRequest;

public interface AppointmentService {
    AppointmentResponse scheduleAppointment(CreateAppointmentRequest request);
    AppointmentResponse getAppointmentById(String appointmentId);
    List<AppointmentResponse> getAppointmentsByPatientId(String patientId);
    List<AppointmentResponse> getAppointmentsByDoctorId(String doctorId);
    List<AppointmentResponse> getAppointmentsBetweenDates(String startDate, String endDate);

}
