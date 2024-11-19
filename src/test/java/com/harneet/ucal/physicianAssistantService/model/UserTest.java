package com.harneet.ucal.physicianAssistantService.model;

import com.harneet.ucal.physicianAssistantService.model.User;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserConstructorAndGetters() {
        // Create the User object using the constructor
        LocalDateTime createdAt = LocalDateTime.now();
        User user = new User();
        user.setUserId(1L);
        user.setName("John Doe");
        user.setPassword("password123");
        user.setAddress("123 Main St");
        user.setCreatedAt(createdAt);
        user.setEmail("johndoe@example.com");
        user.setPhoneNumber("123-456-7890");
        user.setRole("Physician");

        // Assertions to verify the constructor values via setters
        assertEquals(1L, user.getUserId());
        assertEquals("John Doe", user.getName());
        assertEquals("password123", user.getPassword());
        assertEquals("123 Main St", user.getAddress());
        assertEquals(createdAt, user.getCreatedAt());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("123-456-7890", user.getPhoneNumber());
        assertEquals("Physician", user.getRole());
    }

    @Test
    public void testUserSetters() {
        // Create the User object using the default constructor
        User user = new User();

        // Set values using setters
        user.setUserId(2L);
        user.setName("Jane Smith");
        user.setPassword("securePassword");
        user.setAddress("456 Elm St");
        user.setCreatedAt(LocalDateTime.now());
        user.setEmail("janesmith@example.com");
        user.setPhoneNumber("987-654-3210");
        user.setRole("Admin");

        // Assertions to verify the setter values
        assertEquals(2L, user.getUserId());
        assertEquals("Jane Smith", user.getName());
        assertEquals("securePassword", user.getPassword());
        assertEquals("456 Elm St", user.getAddress());
        assertNotNull(user.getCreatedAt());  // Verify createdAt is set to a non-null value
        assertEquals("janesmith@example.com", user.getEmail());
        assertEquals("987-654-3210", user.getPhoneNumber());
        assertEquals("Admin", user.getRole());
    }
}
