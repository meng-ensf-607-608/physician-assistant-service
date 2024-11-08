package com.harneet.ucal.physicianAssistantService.repository;

import com.harneet.ucal.physicianAssistantService.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }

    public User findByUsername(String username) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ?", new UserRowMapper(), username);
    }

    public int save(User user) {
        return jdbcTemplate.update("INSERT INTO users (username, password) VALUES (?, ?)",
                user.getUsername(), user.getPassword());
    }
}
