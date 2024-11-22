package com.harneet.ucal.physicianAssistantService.repository;

import com.harneet.ucal.physicianAssistantService.model.Physician;
import com.harneet.ucal.physicianAssistantService.repository.PhysicianRepository;
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

public class PhysicianRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PhysicianRepository physicianRepository;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByUsername() throws SQLException {
        // Prepare test data
        String username = "test@example.com";
        Physician physician = new Physician();
        physician.setPhysicianId(1L);
        physician.setSpecialisation("Cardiologist");
        physician.setLicense("123456");
        physician.setAcceptingPatients(true);
        physician.setClinicId(1L);

        // Mock the ResultSet behavior
        when(resultSet.getLong("physician_id")).thenReturn(1L);
        when(resultSet.getString("specialisation")).thenReturn("Cardiologist");
        when(resultSet.getString("license")).thenReturn("123456");
        when(resultSet.getBoolean("accepting_patients")).thenReturn(true);
        when(resultSet.getLong("clinic_id")).thenReturn(1L);

        // Mock JdbcTemplate's queryForObject method
        when(jdbcTemplate.queryForObject(eq("SELECT * FROM PHYSICIAN WHERE physician_id IN (SELECT user_id FROM USER WHERE email = ?)"),
                any(PhysicianRepository.PhysicianRowMapper.class), eq(username)))
                .thenReturn(physician);

        // Test method
        Physician result = physicianRepository.findByUsername(username);

        // Assertions
        assertNotNull(result);
        assertEquals(1L, result.getPhysicianId());
        assertEquals("Cardiologist", result.getSpecialisation());
        assertEquals("123456", result.getLicense());
        assertEquals(1L, result.getClinicId());

        // Verify JdbcTemplate's queryForObject method was called once with the correct parameters
        verify(jdbcTemplate, times(1)).queryForObject(eq("SELECT * FROM PHYSICIAN WHERE physician_id IN (SELECT user_id FROM USER WHERE email = ?)"),
                any(PhysicianRepository.PhysicianRowMapper.class), eq(username));
    }

    @Test
    public void testFindAll() throws SQLException {
        // Prepare test data
        Physician physician1 = new Physician();
        physician1.setPhysicianId(1L);
        physician1.setSpecialisation("Cardiologist");
        physician1.setLicense("123456");
        physician1.setAcceptingPatients(true);
        physician1.setClinicId(1L);

        Physician physician2 = new Physician();
        physician2.setPhysicianId(2L);
        physician2.setSpecialisation("Neurologist");
        physician2.setLicense("654321");
        physician2.setAcceptingPatients(false);
        physician2.setClinicId(2L);

        List<Physician> physicianList = Arrays.asList(physician1, physician2);

        // Mock JdbcTemplate's query method
        when(jdbcTemplate.query(eq("SELECT * FROM PHYSICIAN"), any(PhysicianRepository.PhysicianRowMapper.class)))
                .thenReturn(physicianList);

        // Test method
        List<Physician> result = physicianRepository.findAll();

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Cardiologist", result.get(0).getSpecialisation());
        assertEquals("Neurologist", result.get(1).getSpecialisation());

        // Verify JdbcTemplate's query method was called once with the correct parameters
        verify(jdbcTemplate, times(1)).query(eq("SELECT * FROM PHYSICIAN"), any(PhysicianRepository.PhysicianRowMapper.class));
    }

    @Test
    public void testDeleteById() {
        // Prepare test data
        Long physicianId = 1L;

        // Mock JdbcTemplate's update method
        when(jdbcTemplate.update(eq("DELETE FROM PHYSICIAN WHERE physician_id = ?"), eq(physicianId)))
                .thenReturn(1); // Simulate successful deletion

        // Test method
        int rowsAffected = physicianRepository.deleteById(physicianId);

        // Assertions
        assertEquals(1, rowsAffected);

        // Verify JdbcTemplate's update method was called once with the correct parameters
        verify(jdbcTemplate, times(1)).update(eq("DELETE FROM PHYSICIAN WHERE physician_id = ?"), eq(physicianId));
    }
}
