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
public class Appointment {
    /**
     * Unique ID for the appointment.
     */
    private Long appointmentId;

    /**
     * ID of the patient from the Users table.
     */
    private Long patientId;

    /**
     * ID of the physician from the Users table.
     */
    private Long physicianId;

    /**
     * Scheduled appointment start time.
     */
    private LocalDateTime startTime;

    /**
     * Current status of the appointment.
     */
    private String status;

    /**
     * The time the appointment was created.
     */
    private LocalDateTime createdAt;

    /**
     * End time of the appointment.
     */
    private LocalDateTime endTime;
}