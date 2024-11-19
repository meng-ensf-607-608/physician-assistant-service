package com.harneet.ucal.physicianAssistantService.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for AppointmentNote.
 */
public class AppointmentNoteTest {

    @Test
    public void testAppointmentNoteConstructor() {
        // Arrange
        Long appointmentNoteId = 1L;
        Long appointmentId = 100L;
        String symptoms = "Headache, Fever";
        String diagnosis = "Flu";
        String additionalInstructions = "Rest and drink plenty of fluids";

        // Act
        AppointmentNote appointmentNote = new AppointmentNote(appointmentNoteId, appointmentId, symptoms, diagnosis, additionalInstructions);

        // Assert
        assertNotNull(appointmentNote);
        assertEquals(appointmentNoteId, appointmentNote.getAppointmentNoteId());
        assertEquals(appointmentId, appointmentNote.getAppointmentId());
        assertEquals(symptoms, appointmentNote.getSymptoms());
        assertEquals(diagnosis, appointmentNote.getDiagnosis());
        assertEquals(additionalInstructions, appointmentNote.getAdditionalInstructions());
    }

    @Test
    public void testAppointmentNoteDefaultConstructor() {
        // Act
        AppointmentNote appointmentNote = new AppointmentNote();

        // Assert
        assertNotNull(appointmentNote);
        assertNull(appointmentNote.getAppointmentNoteId());
        assertNull(appointmentNote.getAppointmentId());
        assertNull(appointmentNote.getSymptoms());
        assertNull(appointmentNote.getDiagnosis());
        assertNull(appointmentNote.getAdditionalInstructions());
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        AppointmentNote appointmentNote = new AppointmentNote();

        // Act
        appointmentNote.setAppointmentNoteId(1L);
        appointmentNote.setAppointmentId(100L);
        appointmentNote.setSymptoms("Nausea");
        appointmentNote.setDiagnosis("Food Poisoning");
        appointmentNote.setAdditionalInstructions("Avoid spicy food");

        // Assert
        assertEquals(1L, appointmentNote.getAppointmentNoteId());
        assertEquals(100L, appointmentNote.getAppointmentId());
        assertEquals("Nausea", appointmentNote.getSymptoms());
        assertEquals("Food Poisoning", appointmentNote.getDiagnosis());
        assertEquals("Avoid spicy food", appointmentNote.getAdditionalInstructions());
    }
}
