package com.zyphenvisuals.TweeterAPI.exceptions;

public class UsernameTakenException extends RuntimeException {
  public UsernameTakenException(String message) {
    super(message);
  }
}
