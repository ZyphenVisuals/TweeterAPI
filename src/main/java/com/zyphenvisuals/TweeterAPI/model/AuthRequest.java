package com.zyphenvisuals.TweeterAPI.model;

import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
}
