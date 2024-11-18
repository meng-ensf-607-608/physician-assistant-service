package com.harneet.ucal.physicianAssistantService.repository;

import com.harneet.ucal.physicianAssistantService.model.Patient;
import com.harneet.ucal.physicianAssistantService.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PatientRepository patientRepository;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByAppointmentId() throws SQLException {
        // Prepare test data
        Long appointmentId = 1L;
        Patient patient = new Patient();
        patient.setPatientId(1L);
        patient.setHealthCard("12345");
        patient.setGender("M");
        patient.setDateOfBirth(LocalDateTime.of(1990, 1, 1, 0, 0));
        patient.setOccupation("Engineer");
        patient.setChronicConditions("None");
        patient.setDrugAllergies("None");
        patient.setAge(34);  // Assuming current year is 2024
        patient.setEmail("test@example.com");
        patient.setPhoneNumber("123-456-7890");
        patient.setName("John Doe");

        // Mock the ResultSet behavior
        when(resultSet.getLong("patient_id")).thenReturn(1L);
        when(resultSet.getString("health_card")).thenReturn("12345");
        when(resultSet.getString("gender")).thenReturn("M");
        when(resultSet.getTimestamp("date_of_birth")).thenReturn(java.sql.Timestamp.valueOf("1990-01-01 00:00:00"));
        when(resultSet.getString("occupation")).thenReturn("Engineer");
        when(resultSet.getString("chronic_conditions")).thenReturn("None");
        when(resultSet.getString("drug_allergies")).thenReturn("None");
        when(resultSet.getString("email")).thenReturn("test@example.com");
        when(resultSet.getString("phone_number")).thenReturn("123-456-7890");
        when(resultSet.getString("name")).thenReturn("John Doe");

        // Mock JdbcTemplate's queryForObject method
        when(jdbcTemplate.queryForObject(eq("SELECT p.*, u.email, u.phone_number, u.name  FROM PATIENT p JOIN USER u ON p.patient_id = u.user_id WHERE p.patient_id IN (SELECT patient_id FROM APPOINTMENT WHERE appointment_id = ?)"),
                any(PatientRepository.PatientRowMapper.class), eq(appointmentId)))
                .thenReturn(patient);

        // Test method
        Patient result = patientRepository.findByAppointmentId(appointmentId);

        // Assertions
        assertNotNull(result);
        assertEquals(1L, result.getPatientId());
        assertEquals("12345", result.getHealthCard());
        assertEquals("M", result.getGender());
        assertEquals("Engineer", result.getOccupation());
        assertEquals(34, result.getAge());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("123-456-7890", result.getPhoneNumber());
        assertEquals("John Doe", result.getName());

        // Verify JdbcTemplate's queryForObject method was called once with the correct parameters
        verify(jdbcTemplate, times(1)).queryForObject(eq("SELECT p.*, u.email, u.phone_number, u.name  FROM PATIENT p JOIN USER u ON p.patient_id = u.user_id WHERE p.patient_id IN (SELECT patient_id FROM APPOINTMENT WHERE appointment_id = ?)"),
                any(PatientRepository.PatientRowMapper.class), eq(appointmentId));
    }

    @Test
    public void testPatientRowMapper() throws SQLException {
        // Prepare test data
        ResultSet rs = mock(ResultSet.class);
        when(rs.getLong("patient_id")).thenReturn(1L);
        when(rs.getString("health_card")).thenReturn("12345");
        when(rs.getString("gender")).thenReturn("M");
        when(rs.getTimestamp("date_of_birth")).thenReturn(java.sql.Timestamp.valueOf("1990-01-01 00:00:00"));
        when(rs.getString("occupation")).thenReturn("Engineer");
        when(rs.getString("chronic_conditions")).thenReturn("None");
        when(rs.getString("drug_allergies")).thenReturn("None");
        when(rs.getString("email")).thenReturn("test@example.com");
        when(rs.getString("phone_number")).thenReturn("123-456-7890");
        when(rs.getString("name")).thenReturn("John Doe");

        // Create the RowMapper instance
        PatientRepository.PatientRowMapper rowMapper = new PatientRepository.PatientRowMapper();

        // Test the rowMapper's mapRow method
        Patient patient = rowMapper.mapRow(rs, 1);

        // Assertions
        assertNotNull(patient);
        assertEquals(1L, patient.getPatientId());
        assertEquals("12345", patient.getHealthCard());
        assertEquals("M", patient.getGender());
        assertEquals("Engineer", patient.getOccupation());
        assertEquals(34, patient.getAge());  // Age will be calculated as 34 (assuming current year is 2024)
        assertEquals("test@example.com", patient.getEmail());
        assertEquals("123-456-7890", patient.getPhoneNumber());
        assertEquals("John Doe", patient.getName());
    }
}
