package com.zyphenvisuals.TweeterAPI.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordChangeRequest {
    private String old_password;
    private String new_password;
    private String new_password_confirmation;
}
