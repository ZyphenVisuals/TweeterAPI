package com.zyphenvisuals.TweeterAPI.repository;

import com.zyphenvisuals.TweeterAPI.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Integer> {
}