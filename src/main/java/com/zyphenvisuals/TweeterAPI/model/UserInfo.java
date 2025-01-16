package com.zyphenvisuals.TweeterAPI.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
public class UserInfo {
    private String username;
    private Timestamp created;
}
