package com.zyphenvisuals.TweeterAPI.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

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
