package com.zyphenvisuals.TweeterAPI.service;

import com.zyphenvisuals.TweeterAPI.exception.InvalidPasswordException;
import com.zyphenvisuals.TweeterAPI.exception.UsernameTakenException;
import com.zyphenvisuals.TweeterAPI.entity.User;
import com.zyphenvisuals.TweeterAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void registerUser(String username, String password) throws InvalidPasswordException, UsernameTakenException {
        // TODO check password strength

        // check if the user already exists
        User oldUser = userRepository.findByUsername(username);
        if (oldUser != null) {
            throw new UsernameTakenException();
        }

        // compute hashed  password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        // save the user
        userRepository.save(new User(username, hashedPassword));
    }
}
