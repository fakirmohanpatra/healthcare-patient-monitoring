package com.example.healthcare.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreatePatientRequest {
    
    @NotBlank
    private String name;

    @Min(0)
    @Max(120)
    private Integer age;

    @NotBlank
    private String gender;

    private String externalId; // hospital or insurance ID
}
