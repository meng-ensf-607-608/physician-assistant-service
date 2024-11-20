package com.harneet.ucal.physicianAssistantService.repository;

import com.harneet.ucal.physicianAssistantService.model.AppointmentNote;
import com.harneet.ucal.physicianAssistantService.repository.AppointmentNoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppointmentNoteRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AppointmentNoteRepository appointmentNoteRepository;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByAppointmentId() {
        // Prepare test data
        Long appointmentId = 1L;
        AppointmentNote appointmentNote = new AppointmentNote(1L, 1L, "Fever", "Flu", "Take rest");

        // Mock JdbcTemplate behavior
        when(jdbcTemplate.query(
                eq("SELECT * \n" +
                        "FROM APPOINTMENT_NOTE \n" +
                        "WHERE appointment_id IN (\n" +
                        "    SELECT appointment_id \n" +
                        "    FROM APPOINTMENT \n" +
                        "    WHERE patient_id IN (\n" +
                        "        SELECT patient_id \n" +
                        "        FROM APPOINTMENT \n" +
                        "        WHERE appointment_id = ?\n" +
                        "    )\n" +
                        "    AND created_at >= DATE_SUB(CURDATE(), INTERVAL 3 MONTH)\n" +
                        ");"),
                any(AppointmentNoteRepository.AppointmentNoteRowMapper.class),
                eq(appointmentId))
        ).thenReturn(Arrays.asList(appointmentNote));

        // Test method
        List<AppointmentNote> result = appointmentNoteRepository.findByAppointmentId(appointmentId);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Fever", result.get(0).getSymptoms());
        assertEquals("Flu", result.get(0).getDiagnosis());
        assertEquals("Take rest", result.get(0).getAdditionalInstructions());

        // Verify interactions
        verify(jdbcTemplate, times(1)).query(
                eq("SELECT * \n" +
                        "FROM APPOINTMENT_NOTE \n" +
                        "WHERE appointment_id IN (\n" +
                        "    SELECT appointment_id \n" +
                        "    FROM APPOINTMENT \n" +
                        "    WHERE patient_id IN (\n" +
                        "        SELECT patient_id \n" +
                        "        FROM APPOINTMENT \n" +
                        "        WHERE appointment_id = ?\n" +
                        "    )\n" +
                        "    AND created_at >= DATE_SUB(CURDATE(), INTERVAL 3 MONTH)\n" +
                        ");"),
                any(AppointmentNoteRepository.AppointmentNoteRowMapper.class),
                eq(appointmentId)
        );
    }

    @Test
    public void testSave() {
        // Prepare test data
        AppointmentNote appointmentNote = new AppointmentNote(1L, 1L, "Headache", "Migraine", "Take painkiller");

        // Test method
        appointmentNoteRepository.save(Arrays.asList(appointmentNote));

        // Verify that jdbcTemplate.update was called with correct parameters
        verify(jdbcTemplate, times(1)).update(
                eq("INSERT INTO APPOINTMENT_NOTE (appointment_id, symptoms, diagnosis, additional_instructions) VALUES ( ?, ?, ?, ?)"),
                eq(appointmentNote.getAppointmentId()),
                eq(appointmentNote.getSymptoms()),
                eq(appointmentNote.getDiagnosis()),
                eq(appointmentNote.getAdditionalInstructions())
        );
    }

    @Test
    public void testAppointmentNoteRowMapper() throws SQLException {
        // Prepare test data
        ResultSet rs = mock(ResultSet.class);
        when(rs.getLong("appt_note_id")).thenReturn(1L);
        when(rs.getLong("appointment_id")).thenReturn(2L);
        when(rs.getString("symptoms")).thenReturn("Cough");
        when(rs.getString("diagnosis")).thenReturn("Cold");
        when(rs.getString("additional_instructions")).thenReturn("Drink water");

        // Test AppointmentNoteRowMapper
        AppointmentNoteRepository.AppointmentNoteRowMapper rowMapper = new AppointmentNoteRepository.AppointmentNoteRowMapper();
        AppointmentNote appointmentNote = rowMapper.mapRow(rs, 1);

        // Assertions
        assertNotNull(appointmentNote);
        assertEquals(1L, appointmentNote.getAppointmentNoteId());
        assertEquals(2L, appointmentNote.getAppointmentId());
        assertEquals("Cough", appointmentNote.getSymptoms());
        assertEquals("Cold", appointmentNote.getDiagnosis());
        assertEquals("Drink water", appointmentNote.getAdditionalInstructions());
    }
}
