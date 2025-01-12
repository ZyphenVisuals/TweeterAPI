package com.zyphenvisuals.TweeterAPI.repository;

import com.zyphenvisuals.TweeterAPI.entity.Tweet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Integer> {
    List<Tweet> findTweetsByIdBefore(Integer createdBefore, Pageable pageable);

    Tweet findTopByOrderByIdDesc();
}