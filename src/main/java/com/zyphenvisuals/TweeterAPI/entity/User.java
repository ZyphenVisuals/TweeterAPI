package com.zyphenvisuals.TweeterAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    @JsonIgnore
    private int id;

    @Column(unique = true, nullable = false)
    @NonNull
    private String username;

    @Column(nullable = false, columnDefinition = "bpchar")
    @NonNull
    @JsonIgnore
    private String password;

    @Column(nullable = false, insertable = false, updatable = false)
    private Timestamp created;

    @Column(nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    private Timestamp updated;
}
