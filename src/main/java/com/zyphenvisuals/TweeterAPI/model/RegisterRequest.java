package com.zyphenvisuals.TweeterAPI.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String password_confirmation;
}
