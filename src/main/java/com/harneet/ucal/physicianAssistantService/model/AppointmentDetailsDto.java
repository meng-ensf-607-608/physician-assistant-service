package com.harneet.ucal.physicianAssistantService.model;

import lombok.Data;

import java.util.List;

@Data
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
