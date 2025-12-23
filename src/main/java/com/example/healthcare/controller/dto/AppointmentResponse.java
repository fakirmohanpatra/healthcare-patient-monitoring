package com.example.healthcare.controller.dto;

import com.example.healthcare.model.AppointmentStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class AppointmentResponse {
    private String id;
    private String patientId;
    private String doctorId;
    private String appointmentDatetime;
    private AppointmentStatus status;
}
