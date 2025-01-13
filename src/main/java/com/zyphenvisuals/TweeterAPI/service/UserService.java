package com.zyphenvisuals.TweeterAPI.service;

import com.zyphenvisuals.TweeterAPI.exception.IncorrectPasswordException;
import com.zyphenvisuals.TweeterAPI.exception.InvalidPasswordException;
import com.zyphenvisuals.TweeterAPI.exception.UsernameTakenException;
import com.zyphenvisuals.TweeterAPI.entity.User;
import com.zyphenvisuals.TweeterAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private boolean insecurePassword(String password) {
        return password.length() < 8;
    }

    public void registerUser(String username, String password, String password_confirmation) throws InvalidPasswordException, UsernameTakenException {
        // check passwords match
        if(!password.equals(password_confirmation)) {
            throw new InvalidPasswordException();
        }

        // check password strength
        if(insecurePassword(password)) {
            throw new InvalidPasswordException();
        }

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

    public void changePassword(int id, String old_password, String new_password, String new_password_confirmation) throws InvalidPasswordException, IncorrectPasswordException {
        // get the user
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        User user = userOptional.get();

        // check the old password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(!passwordEncoder.matches(old_password, user.getPassword())) {
            throw new IncorrectPasswordException();
        }

        // check the new passwords match
        if(!new_password.equals(new_password_confirmation)) {
            throw new InvalidPasswordException();
        }

        // check the new password is strong enough
        if(insecurePassword(new_password)) {
            throw new InvalidPasswordException();
        }

        // change the password
        String hashedPassword = passwordEncoder.encode(new_password);
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
}
