package com.harneet.ucal.physicianAssistantService.service;

import com.harneet.ucal.physicianAssistantService.model.User;
import com.harneet.ucal.physicianAssistantService.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveValidEmailSavesUser() {
        // Arrange
        String validEmail = "testuser@example.com";
        user = new User();
        user.setEmail(validEmail);
        user.setName("testuser");

        // Act
        userService.save(user);

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void saveInvalidEmailThrowsIllegalArgumentException() {
        // Arrange
        String invalidEmail = "invalidemail.com";
        user = new User();
        user.setEmail(invalidEmail);
        user.setName("testuser");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.save(user);
        });
        assertEquals("Invalid email format", exception.getMessage());
        verify(userRepository, times(0)).save(user); // Verify save was not called
    }

    @Test
    void findByUsernameReturnsUserWhenUserExists() {
        // Arrange
        String username = "testuser";
        String email = "testuser@example.com";
        user = new User();
        user.setName(username);
        user.setEmail(email);

        when(userRepository.findByUsername(username)).thenReturn(user);

        // Act
        User foundUser = userService.findByUsername(username);

        // Assert
        assertNotNull(foundUser);
        assertEquals(username, foundUser.getName());
        assertEquals(email, foundUser.getEmail());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void findByUsernameReturnsNullWhenUserDoesNotExist() {
        // Arrange
        String username = "nonexistentuser";

        when(userRepository.findByUsername(username)).thenReturn(null);

        // Act
        User foundUser = userService.findByUsername(username);

        // Assert
        assertNull(foundUser);
        verify(userRepository, times(1)).findByUsername(username);
    }
}