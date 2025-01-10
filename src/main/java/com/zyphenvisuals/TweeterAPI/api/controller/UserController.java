package com.zyphenvisuals.TweeterAPI.api.controller;

import com.zyphenvisuals.TweeterAPI.api.model.User;
import com.zyphenvisuals.TweeterAPI.exceptions.InvalidPasswordException;
import com.zyphenvisuals.TweeterAPI.exceptions.UsernameTakenException;
import com.zyphenvisuals.TweeterAPI.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User")
@RequestMapping("/api/v1/user/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Register a new user with a username and password. The user will not be logged in automatically, and should be asked to do so themselves."
    )
    public String register(String username, String password) {
        try{
            userService.registerUser(username, password);
            return "User registered successfully";
        } catch (UsernameTakenException | InvalidPasswordException e) {
            return e.getMessage();
        }
    }
}
