package com.zyphenvisuals.TweeterAPI.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    @NonNull
    private String username;

    @Column(nullable = false, columnDefinition = "bpchar")
    @NonNull
    private String password;
}
