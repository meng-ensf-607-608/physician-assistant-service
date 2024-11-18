package com.harneet.ucal.physicianAssistantService.controller;

import com.harneet.ucal.physicianAssistantService.controller.UserController;
import com.harneet.ucal.physicianAssistantService.model.User;
import com.harneet.ucal.physicianAssistantService.service.UserService;
import com.harneet.ucal.physicianAssistantService.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUserByUserName() throws Exception {
        String token = "some-token";
        String username = "testUser";

        User user = new User();
        user.setUserId(1L);
        user.setName("Test User");
        user.setPassword("password123");
        user.setAddress("123 Main St");
        user.setCreatedAt(LocalDateTime.now());
        user.setEmail("testuser@example.com");
        user.setPhoneNumber("123-456-7890");
        user.setRole("Physician");

        // Mock the behavior of JwtUtil and UserService
        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(userService.findByUsername(username)).thenReturn(user);

        mockMvc.perform(get("/v1/user/details")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.password").doesNotExist())  // Ensure password is excluded
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.email").value("testuser@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("123-456-7890"))
                .andExpect(jsonPath("$.role").value("Physician"));

        // Verify that the userService was called once with the correct username
        verify(userService, times(1)).findByUsername(username);
    }
}
