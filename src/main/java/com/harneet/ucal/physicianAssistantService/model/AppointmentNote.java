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
public class AppointmentNote {
    /**
     * Unique ID for the appointment note.
     */
    private Long appointmentNoteId;

    /**
     * Unique ID for the appointment.
     */
    private Long appointmentId;

    /**
     * symptoms for the appointment.
     */
    private String symptoms;

    /**
     * diagnosis for the appointment.
     */
    private String diagnosis;

    /**
     * additional_instructions for the appointment.
     */
    private String additionalInstructions;
}
