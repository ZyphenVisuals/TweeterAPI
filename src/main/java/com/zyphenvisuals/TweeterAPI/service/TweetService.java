package com.zyphenvisuals.TweeterAPI.service;

import com.zyphenvisuals.TweeterAPI.entity.Tweet;
import com.zyphenvisuals.TweeterAPI.exception.TweetTooLongException;
import com.zyphenvisuals.TweeterAPI.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TweetService {
    private final TweetRepository tweetRepository;

    public void postTweet(int userId, String text) throws TweetTooLongException {
        // check text length
        if(text.length() > 300) {
            throw new TweetTooLongException();
        }

        // save the user
        tweetRepository.save(new Tweet(userId, text));
    }
}
