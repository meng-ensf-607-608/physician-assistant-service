package com.harneet.ucal.physicianAssistantService.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PhysicianTest {

    @Test
    public void testPhysicianConstructor() {
        // Creating a Physician object using setters
        Physician physician = new Physician();

        // Setting values for the Physician object
        physician.setPhysicianId(1L);
        physician.setClinicId(101L);
        physician.setSpecialisation("Cardiology");
        physician.setAcceptingPatients(true);
        physician.setLicense("LIC123456");

        // Assertions to check that the values are set correctly
        assertEquals(1L, physician.getPhysicianId());
        assertEquals(101L, physician.getClinicId());
        assertEquals("Cardiology", physician.getSpecialisation());
        assertTrue(physician.getAcceptingPatients());
        assertEquals("LIC123456", physician.getLicense());
    }
}
