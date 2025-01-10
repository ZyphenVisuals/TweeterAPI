package com.zyphenvisuals.TweeterAPI.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health")
public class HealthController {

    @Operation(
            summary = "Health check",
            description = "Health check used for internal monitoring."
    )
    @GetMapping("/api/health")
    public String getHealth() {
        return "Server online.";
    }
}