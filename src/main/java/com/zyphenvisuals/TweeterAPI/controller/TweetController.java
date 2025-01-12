package com.zyphenvisuals.TweeterAPI.controller;

import com.zyphenvisuals.TweeterAPI.entity.Tweet;
import com.zyphenvisuals.TweeterAPI.exception.TweetTooLongException;
import com.zyphenvisuals.TweeterAPI.model.UserPrincipal;
import com.zyphenvisuals.TweeterAPI.service.TweetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Tweet")
@RequestMapping("/api/v1/tweet/")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @PostMapping("/post")
    @Operation(
            summary = "Post a tweet",
            description = "Post a new tweet from the currently logged in account.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Tweet created successfully."),
                    @ApiResponse(responseCode = "400", description = "Tweet too long."),
                    @ApiResponse(responseCode = "403"),
            },
            security = @SecurityRequirement(name = "Token")
    )
    public ResponseEntity<Void> post(String text, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try{
            tweetService.postTweet(userPrincipal.getId(), text);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (TweetTooLongException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
