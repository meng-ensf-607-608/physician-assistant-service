package com.harneet.ucal.physicianAssistantService.service;

import com.harneet.ucal.physicianAssistantService.model.Appointment;
import com.harneet.ucal.physicianAssistantService.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAppointmentsReturnsListOfAppointments() {
        Appointment appointment1 = new Appointment(1L, 201L, 1L, LocalDateTime.now(), "BOOKED", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
        Appointment appointment2 = new Appointment(2L, 202L, 2L, LocalDateTime.now(), "BOOKED", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
        when(appointmentRepository.findAll("asd")).thenReturn(Arrays.asList(appointment1, appointment2));

        List<Appointment> appointments = appointmentService.getAllAppointments("1");

        assertEquals(2, appointments.size());
        verify(appointmentRepository, times(1)).findAll("asd");
    }

    @Test
    void getAppointmentByIdReturnsAppointment() {
        Appointment appointment = new Appointment(1L, 201L, 1L, LocalDateTime.now(), "BOOKED", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
        when(appointmentRepository.findById(1L)).thenReturn(appointment);

        Appointment foundAppointment = appointmentService.getAppointmentById(1L);

        assertEquals(1L, foundAppointment.getAppointmentId());
        verify(appointmentRepository, times(1)).findById(1L);
    }

    @Test
    void getAppointmentsByPhysicianIdReturnsListOfAppointments() {
        Appointment appointment1 = new Appointment(1L, 201L, 1L, LocalDateTime.now(), "BOOKED", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
        Appointment appointment2 = new Appointment(2L, 202L, 1L, LocalDateTime.now(), "BOOKED", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
        when(appointmentRepository.findByPhysicianId(1L)).thenReturn(Arrays.asList(appointment1, appointment2));

        List<Appointment> appointments = appointmentService.getAppointmentsByPhysicianId(1L);

        assertEquals(2, appointments.size());
        verify(appointmentRepository, times(1)).findByPhysicianId(1L);
    }

    @Test
    void createAppointmentSavesAppointment() {
        Appointment appointment = new Appointment(1L, 201L, 1L, LocalDateTime.now(), "BOOKED", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
        when(appointmentRepository.save(appointment)).thenReturn(1);

        int result = appointmentService.saveAppointment(appointment);

        assertEquals(1, result);
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void deleteAppointmentByIdDeletesAppointment() {
        when(appointmentRepository.deleteById(1L)).thenReturn(1);

        int result = appointmentService.deleteAppointment(1L);

        assertEquals(1, result);
        verify(appointmentRepository, times(1)).deleteById(1L);
    }
}