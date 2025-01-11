package com.zyphenvisuals.TweeterAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false, columnDefinition = "bpchar")
    private String password;

    public User(String username, String hashedPassword) {
        this.username = username;
        this.password = hashedPassword;
    }

    public User() {}
}
