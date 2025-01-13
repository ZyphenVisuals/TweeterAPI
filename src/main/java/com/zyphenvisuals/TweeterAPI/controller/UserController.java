package com.zyphenvisuals.TweeterAPI.controller;

import com.zyphenvisuals.TweeterAPI.exception.InvalidPasswordException;
import com.zyphenvisuals.TweeterAPI.exception.UsernameTakenException;
import com.zyphenvisuals.TweeterAPI.model.AuthRequest;
import com.zyphenvisuals.TweeterAPI.model.AuthToken;
import com.zyphenvisuals.TweeterAPI.model.UserPrincipal;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<Void> register(@RequestBody AuthRequest authRequest) {
        try{
            userService.registerUser(authRequest.getUsername(), authRequest.getPassword());
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
    public AuthToken login(@RequestBody AuthRequest request) {

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
            description = "Get a brand new token for the already logged in  user. Used so that I don't have to implement proper refresh tokens.",
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
}
