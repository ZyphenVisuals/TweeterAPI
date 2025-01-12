package com.zyphenvisuals.TweeterAPI.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tweets", schema = "public")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private int id;

    @Column(nullable = false)
    @NonNull
    private Integer creator;

    @Column(nullable = false)
    @NonNull
    private String text;

    @Column(nullable = false, insertable = false, updatable = false)
    private Timestamp created;

    @Column(nullable = false, insertable = false)
    private boolean deleted;
}
