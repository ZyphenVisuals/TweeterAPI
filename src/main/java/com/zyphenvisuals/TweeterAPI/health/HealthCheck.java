package com.zyphenvisuals.TweeterAPI.health;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.openapi.HttpMethod;
import io.javalin.openapi.OpenApi;
import io.javalin.openapi.OpenApiResponse;
import org.jetbrains.annotations.NotNull;
import io.javalin.http.util.NaiveRateLimit;
import java.util.concurrent.TimeUnit;

public class HealthCheck implements Handler {

    @Override
    @OpenApi(
            summary = "Health check ping",
            operationId= "healthcheck",
            path = "/api/health",
            methods = HttpMethod.GET,
            tags = {"Util"},
            responses = {
                    @OpenApiResponse(status = "200"),
                    @OpenApiResponse(status = "429")
            },
            description = "Simple health check ping for the server to monitor. Rate limited to 5 requests per minute."
    )
    public void handle(@NotNull Context context) throws Exception {
        NaiveRateLimit.requestPerTimeUnit(context, 5, TimeUnit.MINUTES);
        context.status(200);
    }
}
