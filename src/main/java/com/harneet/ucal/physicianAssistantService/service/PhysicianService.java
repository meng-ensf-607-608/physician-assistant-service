package com.harneet.ucal.physicianAssistantService.service;

import com.harneet.ucal.physicianAssistantService.model.Physician;
import com.harneet.ucal.physicianAssistantService.repository.PhysicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhysicianService {
    @Autowired
    private PhysicianRepository physicianRepository;

    public List<Physician> getAllPhysicians() {
        return physicianRepository.findAll();
    }

    public Physician getPhysicianById(Long id) {
        return physicianRepository.findById(id);
    }
}