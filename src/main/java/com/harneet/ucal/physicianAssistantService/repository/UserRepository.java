package com.harneet.ucal.physicianAssistantService.repository;

import com.harneet.ucal.physicianAssistantService.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getLong("user_id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setAddress(rs.getString("address"));
            Timestamp createdAt = rs.getTimestamp("created_at");
            if (createdAt!=null) user.setCreatedAt(createdAt.toLocalDateTime());
            user.setEmail(rs.getString("email"));
            user.setPhoneNumber(rs.getString("phone_number"));
            user.setRole(rs.getString("role"));
            return user;
        }
    }

    public User findByUsername(String username) {
        return jdbcTemplate.queryForObject("SELECT * FROM USER WHERE email = ?", new UserRowMapper(), username);
    }

    public void save(User user) {
        jdbcTemplate.update("INSERT INTO USER (name, password, address, created_at, email, phone_number, role) VALUES (?, ?, ?, ?, ?, ?, ?)",
                user.getName(), user.getPassword(), user.getAddress(), user.getCreatedAt(), user.getEmail(), user.getPhoneNumber(), user.getRole());
    }
}