package com.zyphenvisuals.TweeterAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @JoinColumn(nullable = false, name="creator")
    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    @JsonSerialize(as=User.class)
    private User creator;

    @Column(nullable = false)
    @NonNull
    private String text;

    @Column(nullable = false, insertable = false, updatable = false)
    private Timestamp created;

    @Column(nullable = false, insertable = false)
    @JsonIgnore
    private boolean deleted;
}
