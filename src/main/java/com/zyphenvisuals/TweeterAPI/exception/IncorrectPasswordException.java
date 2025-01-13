package com.zyphenvisuals.TweeterAPI.exception;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException() {
        super("The password is incorrect.");
    }
}
