package com.zyphenvisuals.TweeterAPI.repository;

import com.zyphenvisuals.TweeterAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
