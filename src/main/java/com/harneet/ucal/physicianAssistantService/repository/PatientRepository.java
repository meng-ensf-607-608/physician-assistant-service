package com.harneet.ucal.physicianAssistantService.repository;

import com.harneet.ucal.physicianAssistantService.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Repository
public class PatientRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String FIND_BY_APPT_ID = "SELECT p.*, u.email, u.phone_number, u.name  FROM PATIENT p JOIN USER u ON p.patient_id = u.user_id WHERE p.patient_id IN (SELECT patient_id FROM APPOINTMENT WHERE appointment_id = ?)";


    public Patient findByAppointmentId(Long apptId) {
        return jdbcTemplate.queryForObject(FIND_BY_APPT_ID, new PatientRowMapper(), apptId);
    }

    static class PatientRowMapper implements RowMapper<Patient> {
        @Override
        public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
            Patient patient = new Patient();
            patient.setPatientId(rs.getLong("patient_id"));
            patient.setHealthCard(rs.getString("health_card"));
            patient.setGender(rs.getString("gender"));
            patient.setDateOfBirth(rs.getTimestamp("date_of_birth").toLocalDateTime());
            patient.setOccupation(rs.getString("occupation"));
            patient.setChronicConditions(rs.getString("chronic_conditions"));
            patient.setDrugAllergies(rs.getString("drug_allergies"));
            LocalDate birthDate = patient.getDateOfBirth().toLocalDate();
            int age = Period.between(birthDate, LocalDate.now()).getYears();
            patient.setAge(age);
            patient.setEmail(rs.getString("email"));
            patient.setPhoneNumber(rs.getString("phone_number"));
            patient.setName(rs.getString("name"));
            return patient;
        }
    }
}
