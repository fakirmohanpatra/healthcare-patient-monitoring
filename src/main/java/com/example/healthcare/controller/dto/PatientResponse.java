package com.example.healthcare.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class PatientResponse {
    private String id;
    private String name;
    private Integer age;
    private String gender;
    private String externalId; // hospital or insurance ID
}
