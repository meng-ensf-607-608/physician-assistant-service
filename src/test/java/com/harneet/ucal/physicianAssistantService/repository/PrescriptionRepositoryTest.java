package com.harneet.ucal.physicianAssistantService.repository;

import com.harneet.ucal.physicianAssistantService.model.Prescription;
import com.harneet.ucal.physicianAssistantService.repository.PrescriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PrescriptionRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllByAppointmentId() throws SQLException {
        // Prepare test data
        Long appointmentId = 1L;
        Prescription prescription1 = new Prescription();
        prescription1.setPrescriptionId(1L);
        prescription1.setAppointmentId(appointmentId);
        prescription1.setCreatedAt(java.time.LocalDateTime.now());
        prescription1.setMedication("Med1");
        prescription1.setDosage("50mg");
        prescription1.setDuration("7 days");
        prescription1.setFrequency("Twice a day");

        Prescription prescription2 = new Prescription();
        prescription2.setPrescriptionId(2L);
        prescription2.setAppointmentId(appointmentId);
        prescription2.setCreatedAt(java.time.LocalDateTime.now());
        prescription2.setMedication("Med2");
        prescription2.setDosage("100mg");
        prescription2.setDuration("10 days");
        prescription2.setFrequency("Once a day");

        List<Prescription> prescriptionList = Arrays.asList(prescription1, prescription2);

        // Mock ResultSet behavior
        when(resultSet.getLong("prescription_id")).thenReturn(1L);
        when(resultSet.getLong("appointment_id")).thenReturn(appointmentId);
        when(resultSet.getTimestamp("created_at")).thenReturn(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
        when(resultSet.getString("medication")).thenReturn("Med1");
        when(resultSet.getString("dosage")).thenReturn("50mg");
        when(resultSet.getString("duration")).thenReturn("7 days");
        when(resultSet.getString("frequency")).thenReturn("Twice a day");

        // Mock JdbcTemplate's query method
        when(jdbcTemplate.query(eq("SELECT * FROM PRESCRIPTION WHERE appointment_id IN  (SELECT appointment_id FROM APPOINTMENT WHERE patient_id IN (select patient_id from APPOINTMENT where appointment_id = ?))"),
                any(PrescriptionRepository.PrescriptionRowMapper.class), eq(appointmentId)))
                .thenReturn(prescriptionList);

        // Test method
        List<Prescription> result = prescriptionRepository.findAllByAppointmentId(appointmentId);

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Med1", result.get(0).getMedication());
        assertEquals("Med2", result.get(1).getMedication());

        // Verify JdbcTemplate's query method was called once with the correct parameters
        verify(jdbcTemplate, times(1)).query(eq("SELECT * FROM PRESCRIPTION WHERE appointment_id IN  (SELECT appointment_id FROM APPOINTMENT WHERE patient_id IN (select patient_id from APPOINTMENT where appointment_id = ?))"),
                any(PrescriptionRepository.PrescriptionRowMapper.class), eq(appointmentId));
    }

    @Test
    public void testSave() {
        // Prepare test data
        Prescription prescription1 = new Prescription();
        prescription1.setPrescriptionId(1L);
        prescription1.setAppointmentId(1L);
        prescription1.setCreatedAt(java.time.LocalDateTime.now());
        prescription1.setMedication("Med1");
        prescription1.setDosage("50mg");
        prescription1.setDuration("7 days");
        prescription1.setFrequency("Twice a day");

        Prescription prescription2 = new Prescription();
        prescription2.setPrescriptionId(2L);
        prescription2.setAppointmentId(1L);
        prescription2.setCreatedAt(java.time.LocalDateTime.now());
        prescription2.setMedication("Med2");
        prescription2.setDosage("100mg");
        prescription2.setDuration("10 days");
        prescription2.setFrequency("Once a day");

        List<Prescription> prescriptions = Arrays.asList(prescription1, prescription2);

        // Mock JdbcTemplate's update method
        when(jdbcTemplate.update(eq("INSERT INTO PRESCRIPTION (appointment_id, created_at, medication, dosage, duration, frequency) VALUES ( ?, ?, ?, ?, ?, ?)"),
                eq(prescription1.getAppointmentId()), eq(prescription1.getCreatedAt()), eq(prescription1.getMedication()),
                eq(prescription1.getDosage()), eq(prescription1.getDuration()), eq(prescription1.getFrequency())))
                .thenReturn(1); // Simulate successful insertion

        when(jdbcTemplate.update(eq("INSERT INTO PRESCRIPTION (appointment_id, created_at, medication, dosage, duration, frequency) VALUES ( ?, ?, ?, ?, ?, ?)"),
                eq(prescription2.getAppointmentId()), eq(prescription2.getCreatedAt()), eq(prescription2.getMedication()),
                eq(prescription2.getDosage()), eq(prescription2.getDuration()), eq(prescription2.getFrequency())))
                .thenReturn(1); // Simulate successful insertion

        // Test method
        prescriptionRepository.save(prescriptions);

        // Verify JdbcTemplate's update method was called for both prescriptions
        verify(jdbcTemplate, times(1)).update(eq("INSERT INTO PRESCRIPTION (appointment_id, created_at, medication, dosage, duration, frequency) VALUES ( ?, ?, ?, ?, ?, ?)"),
                eq(prescription1.getAppointmentId()), eq(prescription1.getCreatedAt()), eq(prescription1.getMedication()),
                eq(prescription1.getDosage()), eq(prescription1.getDuration()), eq(prescription1.getFrequency()));

        verify(jdbcTemplate, times(1)).update(eq("INSERT INTO PRESCRIPTION (appointment_id, created_at, medication, dosage, duration, frequency) VALUES ( ?, ?, ?, ?, ?, ?)"),
                eq(prescription2.getAppointmentId()), eq(prescription2.getCreatedAt()), eq(prescription2.getMedication()),
                eq(prescription2.getDosage()), eq(prescription2.getDuration()), eq(prescription2.getFrequency()));
    }
}
