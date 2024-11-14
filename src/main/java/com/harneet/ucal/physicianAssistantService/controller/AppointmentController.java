package com.harneet.ucal.physicianAssistantService.controller;

import com.harneet.ucal.physicianAssistantService.model.Appointment;
import com.harneet.ucal.physicianAssistantService.model.AppointmentDetailsDto;
import com.harneet.ucal.physicianAssistantService.service.AppointmentDetailsService;
import com.harneet.ucal.physicianAssistantService.service.AppointmentService;
import com.harneet.ucal.physicianAssistantService.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/appointments")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AppointmentDetailsService appointmentDetailsService;

    @GetMapping("/details/{appointmentId}")
    public AppointmentDetailsDto getAppointmentDetails(@PathVariable Long appointmentId) {
        return appointmentDetailsService.getAppointmentDetails(appointmentId);
    }

    @GetMapping("/all")
    public List<Appointment> getAllAppointments(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        return appointmentService.getAllAppointments(username);
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    @GetMapping("/physician/{physicianId}")
    public List<Appointment> getAppointmentsByPhysicianId(@PathVariable Long physicianId) {
        return appointmentService.getAppointmentsByPhysicianId(physicianId);
    }

    @PostMapping
    public int createAppointment(@RequestBody Appointment appointment) {
        return appointmentService.saveAppointment(appointment);
    }

    @DeleteMapping("/{id}")
    public int deleteAppointment(@PathVariable Long id) {
        return appointmentService.deleteAppointment(id);
    }
}