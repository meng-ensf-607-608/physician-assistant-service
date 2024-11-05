package com.harneet.ucal.physicianAssistantService.repository;

import com.harneet.ucal.physicianAssistantService.model.Physician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PhysicianRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class PhysicianRowMapper implements RowMapper<Physician> {
        @Override
        public Physician mapRow(ResultSet rs, int rowNum) throws SQLException {
            Physician physician = new Physician();
            physician.setPhysicianId(rs.getLong("physician_id"));
            physician.setSpecialization(rs.getString("specialization"));
            physician.setLicense(rs.getString("license"));
            physician.setAcceptingPatients(rs.getBoolean("accepting_patients"));
            physician.setClinicId(rs.getLong("clinic_id"));
            return physician;
        }
    }

    public List<Physician> findAll() {
        return jdbcTemplate.query("SELECT * FROM physician", new PhysicianRowMapper());
    }

    public Physician findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM physician WHERE physician_id = ?", new Object[]{id}, new PhysicianRowMapper());
    }

    public int save(Physician physician) {
        return jdbcTemplate.update("INSERT INTO physician (physician_id, specialization, license, accepting_patients, clinic_id) VALUES (?, ?, ?, ?, ?)",
                physician.getPhysicianId(), physician.getSpecialization(), physician.getLicense(), physician.getAcceptingPatients(), physician.getClinicId());
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM physician WHERE physician_id = ?", id);
    }
}