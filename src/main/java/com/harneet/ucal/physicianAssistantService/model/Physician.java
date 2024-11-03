package com.harneet.ucal.physicianAssistantService.model;

import lombok.Data;

/**
 * Represents a physician with specific details.
 */
@Data
public class Physician {
    /**
     * Unique identifier for each physician (foreign key from Users table's user_id).
     */
    private Long physicianId;

    /**
     * Specialization of the physician.
     */
    private String specialization;

    /**
     * License number of the physician.
     */
    private String license;

    /**
     * Indicates if the physician is accepting new patients.
     */
    private Boolean acceptingPatients;

    /**
     * Foreign key reference to the Clinic table.
     */
    private Long clinicId;
}