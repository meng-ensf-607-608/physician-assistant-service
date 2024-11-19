package com.harneet.ucal.physicianAssistantService.service;

import com.harneet.ucal.physicianAssistantService.model.Physician;
import com.harneet.ucal.physicianAssistantService.repository.PhysicianRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PhysicianServiceTest {

    @InjectMocks
    private PhysicianService physicianService;

    @Mock
    private PhysicianRepository physicianRepository;

    @Mock
    private Physician physician1, physician2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPhysiciansReturnsListOfPhysicians() {
        // Arrange
        when(physicianRepository.findAll()).thenReturn(Arrays.asList(physician1, physician2));

        // Act
        List<Physician> physicians = physicianService.getAllPhysicians();

        // Assert
        assertEquals(2, physicians.size());
        verify(physicianRepository, times(1)).findAll();
    }

    @Test
    void getPhysicianByUserNameReturnsPhysician() {
        // Arrange
        String username = "physicianUsername";
        when(physicianRepository.findByUsername(username)).thenReturn(physician1);

        // Act
        Physician foundPhysician = physicianService.getPhysicianByUserName(username);

        // Assert
        assertEquals(physician1, foundPhysician);
        verify(physicianRepository, times(1)).findByUsername(username);
    }
}