package com.zyphenvisuals.TweeterAPI.controller;

import com.zyphenvisuals.TweeterAPI.entity.User;
import com.zyphenvisuals.TweeterAPI.exception.IncorrectPasswordException;
import com.zyphenvisuals.TweeterAPI.exception.InvalidPasswordException;
import com.zyphenvisuals.TweeterAPI.exception.UsernameTakenException;
import com.zyphenvisuals.TweeterAPI.model.*;
import com.zyphenvisuals.TweeterAPI.service.JwtService;
import com.zyphenvisuals.TweeterAPI.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "User")
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Register a new user with a username and password. The user should then log in to get a token.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created successfully."),
                    @ApiResponse(responseCode = "400", description = "Username or password invalid."),
                    @ApiResponse(responseCode = "409", description = "Username is already taken.")
            }
    )
    public ResponseEntity<Void> register(@RequestBody RegisterRequest registerRequest) {
        try{
            userService.registerUser(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getPassword_confirmation());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (UsernameTakenException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login existing user",
            description = "Login an existing user with username and password. This will return a JSON Web Token to use with future requests.",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "403"),
            }
    )
    public AuthToken login(@RequestBody LoginRequest request) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        var principal = (UserPrincipal) authentication.getPrincipal();

        var token = jwtService.encode(principal.getId(), List.of("USER"));

        return AuthToken.builder()
                .token(token)
                .build();

    }

    @PostMapping("/token")
    @Operation(
            summary = "Get a new token",
            description = "Get a brand new token for the already logged in user. Used so that I don't have to implement proper refresh tokens.",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "403"),
            },
            security = @SecurityRequirement(name = "Token")
    )
    public AuthToken token(@AuthenticationPrincipal UserPrincipal userPrincipal) {


        var token = jwtService.encode(userPrincipal.getId(), List.of("USER"));

        return AuthToken.builder()
                .token(token)
                .build();
    }

    @PostMapping("/change_password")
    @Operation(
            summary = "Change user password",
            description = "Change the password of the currently logged in user.",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "403")
            },
            security = @SecurityRequirement(name = "Token")
    )
    public ResponseEntity<Void> changePassword (@RequestBody PasswordChangeRequest passwordChangeRequest, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            userService.changePassword(userPrincipal.getId(), passwordChangeRequest.getOld_password(), passwordChangeRequest.getNew_password(),  passwordChangeRequest.getNew_password_confirmation());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IncorrectPasswordException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/me")
    @Operation(
            summary = "Get your own user data.",
            description = "Only returns basic User data, not full Profile data.",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "403")
            },
            security = @SecurityRequirement(name = "Token")
    )
    public UserInfo me (@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.getById(userPrincipal.getId());

        return new UserInfo(user.getUsername(), user.getCreated());
    }
}
