package com.zyphenvisuals.TweeterAPI.exception;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException() {
        super("Invalid username or password.");
    }
}
