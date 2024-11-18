package com.harneet.ucal.physicianAssistantService.model;

import com.harneet.ucal.physicianAssistantService.model.Patient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class PatientTest {

    @Test
    public void testPatientConstructor() {

        Patient patient = new Patient();

        // Setting values for the Patient object
        patient.setPatientId(1L);
        patient.setHealthCard("HC123456");
        patient.setGender("Male");
        patient.setAge(30);
        patient.setDateOfBirth(LocalDateTime.of(1993, 1, 15, 0, 0));
        patient.setOccupation("Software Engineer");
        patient.setChronicConditions("None");
        patient.setDrugAllergies("Penicillin");
        patient.setEmail("patient@example.com");
        patient.setPhoneNumber("123-456-7890");
        patient.setName("John Doe");

        // Assertions to check that the values are set correctly
        assertEquals(1L, patient.getPatientId());
        assertEquals("HC123456", patient.getHealthCard());
        assertEquals("Male", patient.getGender());
        assertEquals(30, patient.getAge());
        assertEquals(LocalDateTime.of(1993, 1, 15, 0, 0), patient.getDateOfBirth());
        assertEquals("Software Engineer", patient.getOccupation());
        assertEquals("None", patient.getChronicConditions());
        assertEquals("Penicillin", patient.getDrugAllergies());
        assertEquals("patient@example.com", patient.getEmail());
        assertEquals("123-456-7890", patient.getPhoneNumber());
        assertEquals("John Doe", patient.getName());
    }
}
