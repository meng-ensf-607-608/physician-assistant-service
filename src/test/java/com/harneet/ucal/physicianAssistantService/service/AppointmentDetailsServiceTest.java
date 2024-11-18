package com.harneet.ucal.physicianAssistantService.service;

import com.harneet.ucal.physicianAssistantService.model.AppointmentDetailsDto;
import com.harneet.ucal.physicianAssistantService.model.AppointmentNote;
import com.harneet.ucal.physicianAssistantService.model.Patient;
import com.harneet.ucal.physicianAssistantService.model.Prescription;
import com.harneet.ucal.physicianAssistantService.repository.AppointmentNoteRepository;
import com.harneet.ucal.physicianAssistantService.repository.PatientRepository;
import com.harneet.ucal.physicianAssistantService.repository.PrescriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AppointmentDetailsServiceTest {

    @InjectMocks
    private AppointmentDetailsService appointmentDetailsService;

    @Mock
    private AppointmentNoteRepository appointmentNoteRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private Patient patient;

    @Mock
    private AppointmentNote appointmentNote1, appointmentNote2;

    @Mock
    private Prescription prescription1, prescription2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAppointmentDetailsReturnsCorrectDto() {
        // Arrange
        Long appointmentId = 1L;

        // Mocking the repositories
        when(appointmentNoteRepository.findByAppointmentId(appointmentId))
                .thenReturn(Arrays.asList(appointmentNote1, appointmentNote2));
        when(patientRepository.findByAppointmentId(appointmentId))
                .thenReturn(patient);
        when(prescriptionRepository.findAllByAppointmentId(appointmentId))
                .thenReturn(Arrays.asList(prescription1, prescription2));

        // Act
        AppointmentDetailsDto appointmentDetailsDto = appointmentDetailsService.getAppointmentDetails(appointmentId);

        // Assert
        assertEquals(patient, appointmentDetailsDto.getPatient());
        assertEquals(2, appointmentDetailsDto.getAppointmentNotes().size());
        assertEquals(2, appointmentDetailsDto.getPrescriptions().size());

        verify(appointmentNoteRepository, times(1)).findByAppointmentId(appointmentId);
        verify(patientRepository, times(1)).findByAppointmentId(appointmentId);
        verify(prescriptionRepository, times(1)).findAllByAppointmentId(appointmentId);
    }
}