package com.harneet.ucal.physicianAssistantService.controller;

import com.harneet.ucal.physicianAssistantService.model.Appointment;
import com.harneet.ucal.physicianAssistantService.model.Physician;
import com.harneet.ucal.physicianAssistantService.service.PhysicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/physician")
public class PhysicianController {

    @Autowired
    private PhysicianService physicianService;

    @GetMapping("/all")
    public List<Physician> getAllPhysicians() {
        return physicianService.getAllPhysicians();
    }

    @GetMapping("/{id}")
    public Physician getPhysicianById(@PathVariable Long id) {
        return physicianService.getPhysicianById(id);
    }
}