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

    private static final String FIND_ALL_BY_APPOINTMENT_ID = "SELECT * \n" +
            "FROM PRESCRIPTION \n" +
            "WHERE appointment_id IN (\n" +
            "    SELECT appointment_id \n" +
            "    FROM APPOINTMENT \n" +
            "    WHERE patient_id IN (\n" +
            "        SELECT patient_id \n" +
            "        FROM APPOINTMENT \n" +
            "        WHERE appointment_id = ?\n" +
            "    )\n" +
            "    AND created_at >= DATE_SUB(CURDATE(), INTERVAL 3 MONTH)\n" +
            ");";

    public List<Prescription> findAllByAppointmentId(Long appointmentId) {
        return jdbcTemplate.query(FIND_ALL_BY_APPOINTMENT_ID,  new PrescriptionRowMapper(), appointmentId);
    }

    public void save(List<Prescription> prescriptions) {
        for (Prescription prescription : prescriptions) {
            jdbcTemplate.update("INSERT INTO PRESCRIPTION (appointment_id, created_at, medication, dosage, duration, frequency) VALUES ( ?, ?, ?, ?, ?, ?)",
                    prescription.getAppointmentId(), prescription.getCreatedAt(), prescription.getMedication(), prescription.getDosage(), prescription.getDuration(), prescription.getFrequency());
        }
    }

    static class PrescriptionRowMapper implements RowMapper<Prescription> {
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
