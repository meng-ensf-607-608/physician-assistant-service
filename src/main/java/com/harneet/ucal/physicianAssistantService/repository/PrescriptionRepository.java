package com.harneet.ucal.physicianAssistantService.repository;

import com.harneet.ucal.physicianAssistantService.model.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PrescriptionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_BY_APPOINTMENT_ID = "SELECT * FROM PRESCRIPTION WHERE appointment_id IN  (SELECT * FROM APPOINTMENT WHERE patient_id IN (select patient_id from APPOINTMENT where appointment_id = ?)";
//    private static final String FIND_ALL_APPOINTMENT_FOR_PATIENT = "SELECT * FROM APPOINTMENT WHERE patient_id IN (select patient_id from APPOINTMENT where appointment_id = ?)";

//    public List<Prescription> findAllByAppointmentId(Long appointmentId) {
//        List<Long> allAppointmnets = findAllApptsForPatient(appointmentId);
//        return jdbcTemplate.query(FIND_ALL_BY_APPOINTMENT_ID,  new PrescriptionRowMapper(), allAppointmnets);
//    }

    public List<Prescription> findAllByAppointmentId(Long appointmentId) {
        return jdbcTemplate.query(FIND_ALL_BY_APPOINTMENT_ID,  new PrescriptionRowMapper(), appointmentId);
    }

//    private List<Long> findAllApptsForPatient(Long appointmentId) {
//        return jdbcTemplate.query(FIND_ALL_APPOINTMENT_FOR_PATIENT ,  new AppointmentRowMapper(), appointmentId);
//    }

    private static class PrescriptionRowMapper implements RowMapper<Prescription> {
        @Override
        public Prescription mapRow(ResultSet rs, int rowNum) throws SQLException {
            Prescription prescription = new Prescription();
            prescription.setPrescriptionId(rs.getLong("prescription_id"));
            prescription.setAppointmentId(rs.getLong("appointment_id"));
            prescription.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            prescription.setMedication(rs.getString("medication"));
            prescription.setDosage(rs.getString("dosage"));
            prescription.setDuration(rs.getString("duration"));
            prescription.setFrequency(rs.getString("frequency"));
            return prescription;
        }
    }

    private static class AppointmentRowMapper implements RowMapper<Long> {
        @Override
        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getLong("appointment_id");
        }
    }
}
