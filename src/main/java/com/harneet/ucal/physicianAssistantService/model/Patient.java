package com.harneet.ucal.physicianAssistantService.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Represents a physician with specific details.
 */
@Data
public class Patient {

    private Long patientId;

    private String healthCard;

    private String gender;

    private int age;

    private LocalDateTime dateOfBirth;

    private String occupation;

    private String chronicConditions;

    private String drugAllergies;
}