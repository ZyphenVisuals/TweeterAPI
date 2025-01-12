package com.zyphenvisuals.TweeterAPI.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
}
