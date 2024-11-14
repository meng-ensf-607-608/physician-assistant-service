package com.harneet.ucal.physicianAssistantService.repository;

import com.harneet.ucal.physicianAssistantService.model.Appointment;
import com.harneet.ucal.physicianAssistantService.model.AppointmentDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class AppointmentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private AppointmentNoteRepository appointmentNoteRepository;

    public int updateAppointmentDetails(AppointmentDetailsDto appointmentDetailsDto) {
        prescriptionRepository.save(appointmentDetailsDto.getPrescriptions());
        appointmentNoteRepository.save(appointmentDetailsDto.getAppointmentNotes());
        return 1;
    }

    private static final class AppointmentRowMapper implements RowMapper<Appointment> {
        @Override
        public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Appointment appointment = new Appointment();
            appointment.setAppointmentId(rs.getLong("appointment_id"));
            appointment.setPatientId(rs.getLong("patient_id"));
            appointment.setPhysicianId(rs.getLong("physician_id"));
            appointment.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
            appointment.setStatus(rs.getString("status"));
            appointment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            Timestamp endTime = rs.getTimestamp("end_time");
            if (endTime != null) appointment.setEndTime(endTime.toLocalDateTime());
            return appointment;
        }
    }

    public List<Appointment> findAll(String username) {
        return jdbcTemplate.query("SELECT * FROM APPOINTMENT WHERE physician_id IN (SELECT user_id FROM USER WHERE email = ? ) AND status = 'Scheduled' AND start_time >= NOW()", new AppointmentRowMapper(), username);
    }

    public Appointment findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM APPOINTMENT WHERE appointment_id = ?", new AppointmentRowMapper(), id);
    }

    @Deprecated
    public List<Appointment> findByPhysicianId(Long physicianId) {
        return jdbcTemplate.query("SELECT * FROM APPOINTMENT WHERE physician_id = ?", new AppointmentRowMapper(), physicianId);
    }

    public int save(Appointment appointment) {
        return jdbcTemplate.update("INSERT INTO APPOINTMENT (appointment_id, patient_id, physician_id, start_time, status, created_at, end_time) VALUES (?, ?, ?, ?, ?, ?, ?)",
                appointment.getAppointmentId(), appointment.getPatientId(), appointment.getPhysicianId(), appointment.getStartTime(), appointment.getStatus(), appointment.getCreatedAt(), appointment.getEndTime());
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM APPOINTMENT WHERE appointment_id = ?", id);
    }
}