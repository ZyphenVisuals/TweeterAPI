package com.zyphenvisuals.TweeterAPI.exception;

public class TweetTooLongException extends RuntimeException {
  public TweetTooLongException() {
    super("Maximum tweet length exceeded.");
  }
}
