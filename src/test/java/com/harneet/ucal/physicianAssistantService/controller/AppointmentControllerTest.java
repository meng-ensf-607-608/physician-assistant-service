package com.harneet.ucal.physicianAssistantService.controller;

import com.harneet.ucal.physicianAssistantService.controller.AppointmentController;
import com.harneet.ucal.physicianAssistantService.model.Appointment;
import com.harneet.ucal.physicianAssistantService.service.AppointmentDetailsService;
import com.harneet.ucal.physicianAssistantService.service.AppointmentService;
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

public class AppointmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AppointmentDetailsService appointmentDetailsService;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();
    }

    @Test
    public void testGetAllAppointments() throws Exception {
        String token = "some-token";
        String username = "testUser";

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1L); // Set the correct field name
        List<Appointment> appointments = List.of(appointment);

        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(appointmentService.getAllAppointments(username)).thenReturn(appointments);

        mockMvc.perform(get("/v1/appointments/all")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].appointmentId").value(1L)); // Adjusted to appointmentId

        verify(appointmentService, times(1)).getAllAppointments(username);
    }

    @Test
    public void testGetAppointmentById() throws Exception {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentId); // Set the correct field name

        when(appointmentService.getAppointmentById(appointmentId)).thenReturn(appointment);

        mockMvc.perform(get("/v1/appointments/{id}", appointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointmentId").value(appointmentId)); // Adjusted to appointmentId

        verify(appointmentService, times(1)).getAppointmentById(appointmentId);
    }

    @Test
    public void testGetAppointmentsByPhysicianId() throws Exception {
        Long physicianId = 1L;
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1L);
        List<Appointment> appointments = List.of(appointment);

        when(appointmentService.getAppointmentsByPhysicianId(physicianId)).thenReturn(appointments);

        mockMvc.perform(get("/v1/appointments/physician/{physicianId}", physicianId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].appointmentId").value(1L)); // Adjusted to appointmentId

        verify(appointmentService, times(1)).getAppointmentsByPhysicianId(physicianId);
    }

    @Test
    public void testCreateAppointment() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1L); // Set the correct field name

        when(appointmentService.saveAppointment(appointment)).thenReturn(1);

        mockMvc.perform(post("/v1/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"appointmentId\":1}"))  // Adjusted to appointmentId
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(appointmentService, times(1)).saveAppointment(appointment);
    }

    @Test
    public void testDeleteAppointment() throws Exception {
        Long appointmentId = 1L;

        when(appointmentService.deleteAppointment(appointmentId)).thenReturn(1);

        mockMvc.perform(delete("/v1/appointments/{id}", appointmentId))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(appointmentService, times(1)).deleteAppointment(appointmentId);
    }
}
