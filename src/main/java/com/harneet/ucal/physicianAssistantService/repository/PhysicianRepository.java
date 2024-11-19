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

    public Physician findByUsername(String username) {
        return jdbcTemplate.queryForObject("SELECT * FROM PHYSICIAN WHERE physician_id IN (SELECT user_id FROM USER WHERE email = ?)", new PhysicianRowMapper(), username);
    }

    static final class PhysicianRowMapper implements RowMapper<Physician> {
        @Override
        public Physician mapRow(ResultSet rs, int rowNum) throws SQLException {
            Physician physician = new Physician();
            physician.setPhysicianId(rs.getLong("physician_id"));
            physician.setSpecialisation(rs.getString("specialisation"));
            physician.setLicense(rs.getString("license"));
            physician.setAcceptingPatients(rs.getBoolean("accepting_patients"));
            physician.setClinicId(rs.getLong("clinic_id"));
            return physician;
        }
    }

    public List<Physician> findAll() {
        return jdbcTemplate.query("SELECT * FROM PHYSICIAN", new PhysicianRowMapper());
    }

    public int save(Physician physician) {
        return jdbcTemplate.update("INSERT INTO PHYSICIAN (physician_id, specialisation, license, accepting_patients, clinic_id) VALUES (?, ?, ?, ?, ?)",
                physician.getPhysicianId(), physician.getSpecialisation(), physician.getLicense(), physician.getAcceptingPatients(), physician.getClinicId());
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM PHYSICIAN WHERE physician_id = ?", id);
    }
}