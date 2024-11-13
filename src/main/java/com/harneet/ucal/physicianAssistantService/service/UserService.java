package com.harneet.ucal.physicianAssistantService.service;

import com.harneet.ucal.physicianAssistantService.model.User;
import com.harneet.ucal.physicianAssistantService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
        Pattern.CASE_INSENSITIVE
    );

    public void save(User user) {
        if (isValidEmail(user.getEmail())) {
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).find();
    }
}