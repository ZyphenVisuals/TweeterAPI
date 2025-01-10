package com.zyphenvisuals.TweeterAPI.service;

import com.zyphenvisuals.TweeterAPI.exceptions.InvalidPasswordException;
import com.zyphenvisuals.TweeterAPI.exceptions.UsernameTakenException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;

    public UserService() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public void registerUser(String username, String password) throws InvalidPasswordException, UsernameTakenException {
        // compute hashed  password
        String hashedPassword = passwordEncoder.encode(password);
    }
}
