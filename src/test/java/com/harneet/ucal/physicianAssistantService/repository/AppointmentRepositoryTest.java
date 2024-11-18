package com.harneet.ucal.physicianAssistantService.repository;

import com.harneet.ucal.physicianAssistantService.model.Appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppointmentRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private AppointmentNoteRepository appointmentNoteRepository;

    @InjectMocks
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        // Prepare test data
        String username = "test@example.com";
        Appointment appointment = new Appointment(1L, 123L, 456L, LocalDateTime.now(), "Scheduled", LocalDateTime.now(), null);

        // Mock JdbcTemplate behavior
        when(jdbcTemplate.query(eq("SELECT * FROM APPOINTMENT WHERE physician_id IN (SELECT user_id FROM USER WHERE email = ? ) AND status = 'Scheduled' AND start_time >= NOW()"),
                any(AppointmentRepository.AppointmentRowMapper.class), eq(username)))
                .thenReturn(Arrays.asList(appointment));

        // Test method
        List<Appointment> appointments = appointmentRepository.findAll(username);

        // Assertions
        assertNotNull(appointments);
        assertEquals(1, appointments.size());
        assertEquals(123L, appointments.get(0).getPatientId());
        assertEquals("Scheduled", appointments.get(0).getStatus());

        // Verify that JdbcTemplate's query method was called once with the correct parameters
        verify(jdbcTemplate, times(1)).query(
                eq("SELECT * FROM APPOINTMENT WHERE physician_id IN (SELECT user_id FROM USER WHERE email = ? ) AND status = 'Scheduled' AND start_time >= NOW()"),
                any(AppointmentRepository.AppointmentRowMapper.class), eq(username));
    }

    @Test
    public void testFindById() {
        // Prepare test data
        Long appointmentId = 1L;
        Appointment appointment = new Appointment(1L, 123L, 456L, LocalDateTime.now(), "Scheduled", LocalDateTime.now(), null);

        // Mock JdbcTemplate behavior
        when(jdbcTemplate.queryForObject(eq("SELECT * FROM APPOINTMENT WHERE appointment_id = ?"),
                any(AppointmentRepository.AppointmentRowMapper.class), eq(appointmentId)))
                .thenReturn(appointment);

        // Test method
        Appointment result = appointmentRepository.findById(appointmentId);

        // Assertions
        assertNotNull(result);
        assertEquals(appointmentId, result.getAppointmentId());

        // Verify that JdbcTemplate's queryForObject method was called once
        verify(jdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM APPOINTMENT WHERE appointment_id = ?"),
                any(AppointmentRepository.AppointmentRowMapper.class), eq(appointmentId));
    }


    @Test
    public void testDeleteById() {
        // Prepare test data
        Long appointmentId = 1L;

        // Mock JdbcTemplate behavior
        when(jdbcTemplate.update(eq("DELETE FROM APPOINTMENT WHERE appointment_id = ?"), eq(appointmentId)))
                .thenReturn(1);

        // Test method
        int rowsAffected = appointmentRepository.deleteById(appointmentId);

        // Assertions
        assertEquals(1, rowsAffected);

        // Verify that JdbcTemplate's update method was called once
        verify(jdbcTemplate, times(1)).update(eq("DELETE FROM APPOINTMENT WHERE appointment_id = ?"), eq(appointmentId));
    }


}
