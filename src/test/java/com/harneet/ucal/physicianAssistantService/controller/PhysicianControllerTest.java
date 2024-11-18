package com.harneet.ucal.physicianAssistantService.controller;

import com.harneet.ucal.physicianAssistantService.controller.PhysicianController;
import com.harneet.ucal.physicianAssistantService.model.Physician;
import com.harneet.ucal.physicianAssistantService.service.PhysicianService;
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

import java.util.List;

public class PhysicianControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PhysicianService physicianService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private PhysicianController physicianController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(physicianController).build();
    }

    @Test
    public void testGetAllPhysicians() throws Exception {
        Physician physician = new Physician();
        physician.setPhysicianId(1L);
        physician.setClinicId(1L);
        physician.setSpecialisation("Cardiology");
        physician.setAcceptingPatients(true);
        physician.setLicense("12345");

        List<Physician> physicians = List.of(physician);

        when(physicianService.getAllPhysicians()).thenReturn(physicians);

        mockMvc.perform(get("/v1/physician/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].physicianId").value(1L))
                .andExpect(jsonPath("$[0].specialisation").value("Cardiology"))
                .andExpect(jsonPath("$[0].acceptingPatients").value(true));

        verify(physicianService, times(1)).getAllPhysicians();
    }

    @Test
    public void testGetPhysicianByUserName() throws Exception {
        String token = "some-token";
        String username = "testUser";

        Physician physician = new Physician();
        physician.setPhysicianId(1L);
        physician.setClinicId(1L);
        physician.setSpecialisation("Cardiology");
        physician.setAcceptingPatients(true);
        physician.setLicense("12345");

        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(physicianService.getPhysicianByUserName(username)).thenReturn(physician);

        mockMvc.perform(get("/v1/physician/details")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.physicianId").value(1L))
                .andExpect(jsonPath("$.specialisation").value("Cardiology"))
                .andExpect(jsonPath("$.acceptingPatients").value(true));

        verify(physicianService, times(1)).getPhysicianByUserName(username);
    }
}
