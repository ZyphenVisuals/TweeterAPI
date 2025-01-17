package com.zyphenvisuals.TweeterAPI.service;

import com.zyphenvisuals.TweeterAPI.entity.Tweet;
import com.zyphenvisuals.TweeterAPI.entity.User;
import com.zyphenvisuals.TweeterAPI.exception.TweetTooLongException;
import com.zyphenvisuals.TweeterAPI.repository.TweetRepository;
import com.zyphenvisuals.TweeterAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetService {
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public Tweet postTweet(int userId, String text) throws TweetTooLongException {
        // check text length
        if(text.length() > 300) {
            throw new TweetTooLongException();
        }

        // get the user
        Optional<User> creatorOptional = userRepository.findById(userId);
        if(creatorOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        User user = creatorOptional.get();

        // save the user
        Tweet newTweet = new Tweet(user, text);
        tweetRepository.save(newTweet);
        return newTweet;
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
