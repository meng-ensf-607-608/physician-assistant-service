package com.harneet.ucal.physicianAssistantService.controller;

import com.harneet.ucal.physicianAssistantService.model.Physician;
import com.harneet.ucal.physicianAssistantService.service.PhysicianService;
import com.harneet.ucal.physicianAssistantService.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/physician")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PhysicianController {

    @Autowired
    private PhysicianService physicianService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/all")
    public List<Physician> getAllPhysicians() {
        return physicianService.getAllPhysicians();
    }

    @GetMapping("/details")
    public Physician getPhysicianByUserName(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        return physicianService.getPhysicianByUserName(username);
    }
}