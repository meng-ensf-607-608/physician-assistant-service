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
     * Scheduled appointment time.
     */
    private LocalDateTime appointmentTime;

    /**
     * Current status of the appointment.
     */
    private String status;

    /**
     * If rescheduled, the new appointment time (NULL if not rescheduled).
     */
    private LocalDateTime rescheduledTime;

    /**
     * The time the appointment was booked.
     */
    private LocalDateTime bookingTime;

    /**
     * End time of the appointment.
     */
    private LocalDateTime appointmentEndTime;
}