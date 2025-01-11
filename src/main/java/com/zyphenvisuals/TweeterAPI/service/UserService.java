package com.zyphenvisuals.TweeterAPI.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zyphenvisuals.TweeterAPI.exception.InvalidLoginException;
import com.zyphenvisuals.TweeterAPI.exception.InvalidPasswordException;
import com.zyphenvisuals.TweeterAPI.exception.UsernameTakenException;
import com.zyphenvisuals.TweeterAPI.model.User;
import com.zyphenvisuals.TweeterAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;
import java.util.Date;

@Service
public class UserService {

    private final Algorithm algorithm;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public UserService(@Value("${jwt.secret}") String jwtSecret) {
        passwordEncoder = new BCryptPasswordEncoder(12);
        algorithm = Algorithm.HMAC256(jwtSecret);
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

    public String loginUser(String username, String password) throws InvalidLoginException, UnknownServiceException {
        // check if the user exists
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new InvalidLoginException("Invalid username or password.");
        }

        // check if the password matches
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new InvalidLoginException("Invalid username or password.");
        }

        // generate JWT
        try{
            return JWT.create()
                    .withSubject(String.valueOf(user.getId())) // FIXME sequential ID exposed to the user
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                    .sign(algorithm);
        } catch (Exception e ) {
            throw new UnknownServiceException(e.getMessage());
        }
    }
}
