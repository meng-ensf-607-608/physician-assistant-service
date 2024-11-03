package com.harneet.ucal.physicianAssistantService.repository;

import com.harneet.ucal.physicianAssistantService.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AppointmentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class AppointmentRowMapper implements RowMapper<Appointment> {
        @Override
        public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Appointment appointment = new Appointment();
            appointment.setAppointmentId(rs.getLong("appointment_id"));
            appointment.setPatientId(rs.getLong("patient_id"));
            appointment.setPhysicianId(rs.getLong("physician_id"));
            appointment.setAppointmentTime(rs.getTimestamp("appointment_time").toLocalDateTime());
            appointment.setStatus(rs.getString("status"));
            appointment.setRescheduledTime(rs.getTimestamp("rescheduled_time") != null ? rs.getTimestamp("rescheduled_time").toLocalDateTime() : null);
            appointment.setBookingTime(rs.getTimestamp("booking_time").toLocalDateTime());
            appointment.setAppointmentEndTime(rs.getTimestamp("appointment_end_time").toLocalDateTime());
            return appointment;
        }
    }

    public List<Appointment> findAll() {
        return jdbcTemplate.query("SELECT * FROM appointments", new AppointmentRowMapper());
    }

    public Appointment findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM appointments WHERE appointment_id = ?", new AppointmentRowMapper(), id);
    }

    public List<Appointment> findByPhysicianId(Long physicianId) {
        return jdbcTemplate.query("SELECT * FROM appointments WHERE physician_id = ?", new AppointmentRowMapper(), physicianId);
    }

    public int save(Appointment appointment) {
        return jdbcTemplate.update("INSERT INTO appointments (appointment_id, patient_id, physician_id, appointment_time, status, rescheduled_time, booking_time, appointment_end_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                appointment.getAppointmentId(), appointment.getPatientId(), appointment.getPhysicianId(), appointment.getAppointmentTime(), appointment.getStatus(), appointment.getRescheduledTime(), appointment.getBookingTime(), appointment.getAppointmentEndTime());
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM appointments WHERE appointment_id = ?", id);
    }
}