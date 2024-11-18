package com.harneet.ucal.physicianAssistantService.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class PrescriptionTest {

    @Test
    public void testPrescriptionConstructorAndGetters() {
        // Create the Prescription object using the constructor
        LocalDateTime createdAt = LocalDateTime.now();
        Prescription prescription = new Prescription(
                1L, // prescriptionId
                100L, // appointmentId
                createdAt, // createdAt
                "Aspirin", // medication
                "500mg", // dosage
                "5 days", // duration
                "Twice a day" // frequency
        );

        // Assertions to verify the constructor values
        assertEquals(1L, prescription.getPrescriptionId());
        assertEquals(100L, prescription.getAppointmentId());
        assertEquals(createdAt, prescription.getCreatedAt());
        assertEquals("Aspirin", prescription.getMedication());
        assertEquals("500mg", prescription.getDosage());
        assertEquals("5 days", prescription.getDuration());
        assertEquals("Twice a day", prescription.getFrequency());
    }

    @Test
    public void testPrescriptionSetters() {
        // Create the Prescription object using the default constructor
        Prescription prescription = new Prescription();

        // Set values using setters
        prescription.setPrescriptionId(2L);
        prescription.setAppointmentId(200L);
        prescription.setCreatedAt(LocalDateTime.now());
        prescription.setMedication("Ibuprofen");
        prescription.setDosage("200mg");
        prescription.setDuration("7 days");
        prescription.setFrequency("Once a day");

        // Assertions to verify the setter values
        assertEquals(2L, prescription.getPrescriptionId());
        assertEquals(200L, prescription.getAppointmentId());
        assertNotNull(prescription.getCreatedAt());  // Verify createdAt is set to a non-null value
        assertEquals("Ibuprofen", prescription.getMedication());
        assertEquals("200mg", prescription.getDosage());
        assertEquals("7 days", prescription.getDuration());
        assertEquals("Once a day", prescription.getFrequency());
    }
}
