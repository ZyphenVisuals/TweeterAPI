package com.zyphenvisuals.TweeterAPI.controller;

import com.zyphenvisuals.TweeterAPI.entity.Tweet;
import com.zyphenvisuals.TweeterAPI.exception.TweetTooLongException;
import com.zyphenvisuals.TweeterAPI.model.PostTweetRequest;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Tag(name = "Tweet")
@RequestMapping("/api/v1/tweet/")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @PostMapping(value = "/post")
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
    public Tweet post(@RequestBody PostTweetRequest request
            , @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try{
            return tweetService.postTweet(userPrincipal.getId(), request.getText());
        } catch (TweetTooLongException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")
    @Operation(
            summary = "Get a list of tweets",
            description = "Get a list of the newest tweets. If a 'before' parameter is given, then it will return the latest tweets before that timestamp, for pagination.",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "403"),
            },
            security = @SecurityRequirement(name = "Token")
    )
    public List<Tweet> list(@RequestParam(required = false) Integer before) {

        return tweetService.getTweets(before);
    }

    @GetMapping("/checkNewer")
    @Operation(
            summary = "Check for newer tweets",
            description = "The client should periodically check if newer tweets are available. If this returns true,the  client is responsible for loading them.",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "403"),
            },
            security = @SecurityRequirement(name = "Token")
    )
    public Boolean checkNewer(@RequestParam int id) {
        return tweetService.checkNewer(id);
    }
}
