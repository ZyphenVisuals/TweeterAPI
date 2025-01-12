package com.zyphenvisuals.TweeterAPI.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthToken {
    private String token;
}
