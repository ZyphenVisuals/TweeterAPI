package com.zyphenvisuals.TweeterAPI.controller;

import com.zyphenvisuals.TweeterAPI.exception.InvalidLoginException;
import com.zyphenvisuals.TweeterAPI.exception.InvalidPasswordException;
import com.zyphenvisuals.TweeterAPI.exception.UsernameTakenException;
import com.zyphenvisuals.TweeterAPI.model.User;
import com.zyphenvisuals.TweeterAPI.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownServiceException;

@RestController
@Tag(name = "User")
@RequestMapping("/api/v1/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Register a new user with a username and password. The user will not be logged in automatically, and should be asked to do so themselves.",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "409")
            }
    )
    public ResponseEntity<String> register(String username, String password) {
        try{
            userService.registerUser(username, password);
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } catch (UsernameTakenException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login existing user",
            description = "Login an existing user with username and password. This will return a JSON Web Token to use with future requests.",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "401"),
                    @ApiResponse(responseCode = "500")
            }
    )
    public ResponseEntity<String> login(String username, String password) {
        try{
            String jwt = userService.loginUser(username, password);
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (InvalidLoginException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (UnknownServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
