package com.harneet.ucal.physicianAssistantService.service;

import com.harneet.ucal.physicianAssistantService.model.AppointmentDetailsDto;
import com.harneet.ucal.physicianAssistantService.model.AppointmentNote;
import com.harneet.ucal.physicianAssistantService.model.Patient;
import com.harneet.ucal.physicianAssistantService.model.Prescription;
import com.harneet.ucal.physicianAssistantService.repository.AppointmentNoteRepository;
import com.harneet.ucal.physicianAssistantService.repository.PatientRepository;
import com.harneet.ucal.physicianAssistantService.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentDetailsService {

    @Autowired
    private AppointmentNoteRepository appointmentNoteRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public AppointmentDetailsDto getAppointmentDetails(Long appointmentId) {
        List<AppointmentNote> appointmentNotes = appointmentNoteRepository.findByAppointmentId(appointmentId);
        Patient patient = patientRepository.findByAppointmentId(appointmentId);
        List<Prescription> prescriptions = prescriptionRepository.findAllByAppointmentId(appointmentId);

        return new AppointmentDetailsDto(patient, appointmentNotes, prescriptions);
    }
}
