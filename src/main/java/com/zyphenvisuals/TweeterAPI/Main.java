package com.zyphenvisuals.TweeterAPI;

import com.zyphenvisuals.TweeterAPI.account.AccountLogin;
import com.zyphenvisuals.TweeterAPI.health.HealthCheck;
import io.javalin.Javalin;

import io.javalin.openapi.HttpMethod;
import io.javalin.openapi.OpenApi;
import io.javalin.openapi.OpenApiResponse;
import io.javalin.openapi.plugin.OpenApiPlugin;
import io.javalin.openapi.plugin.SecurityComponentConfiguration;
import io.javalin.openapi.plugin.swagger.SwaggerPlugin;

public class Main {
    public static void main(String[] args) {
        //  config
        String deprecatedDocsPath = "/api/openapi.json";
        int port  = 3000;

        // create the app
        var app = Javalin.create(config -> {
            config.registerPlugin(new OpenApiPlugin(openApiConfig ->
                    openApiConfig
                            .withDocumentationPath(deprecatedDocsPath)
                            .withDefinitionConfiguration((version, openApiDefinition) ->
                                    openApiDefinition
                                            .withInfo(openApiInfo ->
                                                    openApiInfo
                                                            .description("REST API for Tweeter, my final Programming 3 project.")
                                                            .contact("ZyphenVisuals", "", "contact@zyphenvisuals.com")
                                                            .title("TweeterAPI")
                                            )
                                            // Based on official example: https://swagger.io/docs/specification/authentication/oauth2/
                                            .withSecurity(SecurityComponentConfiguration::withBearerAuth
                                            )
                            )));

            config.registerPlugin(new SwaggerPlugin(swaggerConfiguration -> {
                swaggerConfiguration.setDocumentationPath(deprecatedDocsPath);
            }));
        });

        // set up routes
        // account related
        app.post("/api/account/login", new AccountLogin());
        // health check
        app.get("/api/health", new HealthCheck());

        // start the app
        app.start(port);


    }
}