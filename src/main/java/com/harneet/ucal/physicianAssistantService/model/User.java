package com.harneet.ucal.physicianAssistantService.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long userId;
    private String name;
    private String password;
    private String address;
    private LocalDateTime createdAt;
    private String email;
    private String phoneNumber;
    private String role;
}