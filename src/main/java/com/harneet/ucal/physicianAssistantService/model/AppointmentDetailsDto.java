package com.harneet.ucal.physicianAssistantService.model;

import com.harneet.ucal.physicianAssistantService.model.AppointmentNote;
import com.harneet.ucal.physicianAssistantService.model.Patient;
import com.harneet.ucal.physicianAssistantService.model.Prescription;
import java.util.List;

public class AppointmentDetailsDto {
    private Patient patient;
    private List<AppointmentNote> appointmentNotes;
    private List<Prescription> prescriptions;

    public AppointmentDetailsDto(Patient patient, List<AppointmentNote> appointmentNotes, List<Prescription> prescriptions) {
        this.patient = patient;
        this.appointmentNotes = appointmentNotes;
        this.prescriptions = prescriptions;
    }

}
