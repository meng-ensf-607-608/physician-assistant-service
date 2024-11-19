package com.harneet.ucal.physicianAssistantService.repository;

import com.harneet.ucal.physicianAssistantService.model.AppointmentNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AppointmentNoteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String FIND_BY_APPOINTMENT_ID = "SELECT * FROM APPOINTMENT_NOTE WHERE appointment_id IN  (SELECT appointment_id FROM APPOINTMENT WHERE patient_id IN (select patient_id from APPOINTMENT where appointment_id = ?))";

    public List<AppointmentNote> findByAppointmentId(Long appointmentId) {
        return jdbcTemplate.query(FIND_BY_APPOINTMENT_ID, new AppointmentNoteRowMapper(), appointmentId);
    }

    public void save(List<AppointmentNote> appointmentNotes) {
        for (AppointmentNote appointmentNote : appointmentNotes) {
            jdbcTemplate.update("INSERT INTO APPOINTMENT_NOTE (appointment_id, symptoms, diagnosis, additional_instructions) VALUES ( ?, ?, ?, ?)",
                    appointmentNote.getAppointmentId(), appointmentNote.getSymptoms(), appointmentNote.getDiagnosis(), appointmentNote.getAdditionalInstructions());
        }
    }

    static class AppointmentNoteRowMapper implements RowMapper<AppointmentNote> {
        @Override
        public AppointmentNote mapRow(ResultSet rs, int rowNum) throws SQLException {
            AppointmentNote appointmentNote = new AppointmentNote();
            appointmentNote.setAppointmentNoteId(rs.getLong("appt_note_id"));
            appointmentNote.setAppointmentId(rs.getLong("appointment_id"));
            appointmentNote.setSymptoms(rs.getString("symptoms"));
            appointmentNote.setDiagnosis(rs.getString("diagnosis"));
            appointmentNote.setAdditionalInstructions(rs.getString("additional_instructions"));
            return appointmentNote;
        }
    }

    private static class AppointmentRowMapper implements RowMapper<Long> {
        @Override
        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getLong("appointment_id");
        }
    }
}
