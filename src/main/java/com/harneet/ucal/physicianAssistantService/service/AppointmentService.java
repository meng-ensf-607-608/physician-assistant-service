package com.harneet.ucal.physicianAssistantService.service;

import com.harneet.ucal.physicianAssistantService.model.Appointment;
import com.harneet.ucal.physicianAssistantService.model.AppointmentDetailsDto;
import com.harneet.ucal.physicianAssistantService.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments(String username) {
        return appointmentRepository.findAll(username);
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public List<Appointment> getAppointmentsByPhysicianId(Long physicianId) {
        return appointmentRepository.findByPhysicianId(physicianId);
    }

    public int saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public int deleteAppointment(Long id) {
        return appointmentRepository.deleteById(id);
    }

    public String updateAppointmentDetails(AppointmentDetailsDto appointmentDetailsDto) {
        appointmentRepository.updateAppointmentDetails(appointmentDetailsDto);
        return "Updated";
    }
}