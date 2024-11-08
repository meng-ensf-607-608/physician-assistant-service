package com.harneet.ucal.physicianAssistantService.service;

import com.harneet.ucal.physicianAssistantService.model.User;
import com.harneet.ucal.physicianAssistantService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }
}
