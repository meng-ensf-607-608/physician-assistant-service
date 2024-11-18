package com.harneet.ucal.physicianAssistantService.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentDetailsDtoTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange: Create a Patient object, AppointmentNote objects, and Prescription objects
        Patient patient = new Patient();
        patient.setPatientId(1L);
        patient.setName("John Doe");
        patient.setEmail("john.doe@example.com");

        AppointmentNote appointmentNote1 = new AppointmentNote(1L, 101L, "Headache", "Common cold", "Take rest");
        AppointmentNote appointmentNote2 = new AppointmentNote(2L, 101L, "Cough", "Flu", "Drink fluids");

        Prescription prescription1 = new Prescription(1L, 101L, LocalDateTime.now(), "Paracetamol", "500mg", "7 days", "Twice a day");
        Prescription prescription2 = new Prescription(2L, 101L, LocalDateTime.now(), "Ibuprofen", "200mg", "5 days", "Once a day");


        // Create AppointmentDetailsDto with the above objects
        AppointmentDetailsDto appointmentDetailsDto = new AppointmentDetailsDto(
                patient,
                Arrays.asList(appointmentNote1, appointmentNote2),
                Arrays.asList(prescription1, prescription2)
        );

        // Assert: Verify the fields in the AppointmentDetailsDto object
        assertEquals(patient, appointmentDetailsDto.getPatient());
        assertEquals(2, appointmentDetailsDto.getAppointmentNotes().size());
        assertTrue(appointmentDetailsDto.getAppointmentNotes().contains(appointmentNote1));
        assertTrue(appointmentDetailsDto.getAppointmentNotes().contains(appointmentNote2));
        assertEquals(2, appointmentDetailsDto.getPrescriptions().size());
        assertTrue(appointmentDetailsDto.getPrescriptions().contains(prescription1));
        assertTrue(appointmentDetailsDto.getPrescriptions().contains(prescription2));
    }
    
}
