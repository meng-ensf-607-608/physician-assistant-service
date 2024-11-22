package com.harneet.ucal.physicianAssistantService.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    @Test
    void testAppointmentConstructorAndGettersSetters() {
        // Arrange: Create a new Appointment object using the all-args constructor
        Long appointmentId = 1L;
        Long patientId = 101L;
        Long physicianId = 202L;
        LocalDateTime startTime = LocalDateTime.of(2024, 11, 17, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 11, 17, 11, 0);
        String status = "Scheduled";
        LocalDateTime createdAt = LocalDateTime.now();

        Appointment appointment = new Appointment(appointmentId, patientId, physicianId, startTime, status, createdAt, endTime);

        // Assert: Check that all fields are correctly set via constructor
        assertEquals(appointmentId, appointment.getAppointmentId());
        assertEquals(patientId, appointment.getPatientId());
        assertEquals(physicianId, appointment.getPhysicianId());
        assertEquals(startTime, appointment.getStartTime());
        assertEquals(status, appointment.getStatus());
        assertEquals(createdAt, appointment.getCreatedAt());
        assertEquals(endTime, appointment.getEndTime());

        // Act: Modify some fields using setters
        appointment.setStatus("Completed");
        appointment.setEndTime(LocalDateTime.of(2024, 11, 17, 12, 0));

        // Assert: Check if the setter methods work correctly
        assertEquals("Completed", appointment.getStatus());
        assertEquals(LocalDateTime.of(2024, 11, 17, 12, 0), appointment.getEndTime());
    }

    @Test
    void testNoArgsConstructor() {
        // Act: Create a new Appointment object using the no-args constructor
        Appointment appointment = new Appointment();

        // Assert: Check that fields are initialized to null or default values
        assertNull(appointment.getAppointmentId());
        assertNull(appointment.getPatientId());
        assertNull(appointment.getPhysicianId());
        assertNull(appointment.getStartTime());
        assertNull(appointment.getStatus());
        assertNull(appointment.getCreatedAt());
        assertNull(appointment.getEndTime());
    }
}
