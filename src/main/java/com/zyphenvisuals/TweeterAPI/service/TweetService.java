package com.zyphenvisuals.TweeterAPI.service;

import com.zyphenvisuals.TweeterAPI.entity.Tweet;
import com.zyphenvisuals.TweeterAPI.exception.TweetTooLongException;
import com.zyphenvisuals.TweeterAPI.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import java.util.List;

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

    public List<Tweet> getTweets(@Nullable Integer before) {
        if(before == null) {
            return tweetRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "created"))).toList();
        }
        return tweetRepository.findTweetsByIdBefore(before,PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "created")));
    }

    public Boolean checkNewer(int id){
        Tweet latest = tweetRepository.findTopByOrderByIdDesc();
        return latest.getId() > id;
    }
}
