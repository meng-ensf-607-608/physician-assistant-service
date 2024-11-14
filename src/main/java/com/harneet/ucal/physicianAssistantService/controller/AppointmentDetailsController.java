package com.harneet.ucal.physicianAssistantService.controller;

import com.harneet.ucal.physicianAssistantService.model.AppointmentDetailsDto;
import com.harneet.ucal.physicianAssistantService.service.AppointmentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/appointment-details")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AppointmentDetailsController {

    @Autowired
    private AppointmentDetailsService appointmentDetailsService;

    @GetMapping("/{appointmentId}")
    public AppointmentDetailsDto getAppointmentDetails(@PathVariable Long appointmentId) {
        return appointmentDetailsService.getAppointmentDetails(appointmentId);
    }
}