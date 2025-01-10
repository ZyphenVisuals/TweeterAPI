package com.zyphenvisuals.TweeterAPI.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "TweeterAPI",
                description = "API for Tweeter, my final P3 project.",
                contact = @Contact(
                        name = "Zyphen",
                        email = "contact@zyphenvisuals.com",
                        url = "https://zyphenvisuals.com"
                ),
                version = "v1"
        ),
        servers = {
                @Server(
                        description = "Development",
                        url = "http://localhost:8080"
                )
        }
)
public class OpenAPIConfig {
}
