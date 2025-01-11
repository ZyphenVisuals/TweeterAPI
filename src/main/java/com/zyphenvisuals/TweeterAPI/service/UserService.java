package com.zyphenvisuals.TweeterAPI.service;

import com.zyphenvisuals.TweeterAPI.exception.InvalidPasswordException;
import com.zyphenvisuals.TweeterAPI.exception.UsernameTakenException;
import com.zyphenvisuals.TweeterAPI.model.User;
import com.zyphenvisuals.TweeterAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public UserService() {
        passwordEncoder = new BCryptPasswordEncoder(12);
    }

    public void registerUser(String username, String password) throws InvalidPasswordException, UsernameTakenException {
        // TODO check password strength

        // check if the user already exists
        User oldUser = userRepository.findByUsername(username);
        if (oldUser != null) {
            throw new UsernameTakenException("Username is already taken.");
        }

        // compute hashed  password
        String hashedPassword = passwordEncoder.encode(password);

        // save the user
        userRepository.save(new User(username, hashedPassword));
    }
}
