package com.harneet.ucal.physicianAssistantService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents an appointment with specific details.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prescription {
    /**
     * Unique ID for the prescription.
     */
    private Long prescriptionId;

    /**
     * Unique ID for the appointment the presciption is linked to.
     */
    private Long appointmentId;

    /**
     * creation time for the prescription.
     */
    private LocalDateTime createdAt;

    /**
     * medication for the prescription.
     */
    private String medication;

    /**
     * dosage for the prescription.
     */
    private String dosage;

    /**
     * duration of the prescription.
     */
    private String duration;

    /**
     * frequency of the prescription.
     */
    private String frequency;
}
