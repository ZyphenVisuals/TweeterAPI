package com.zyphenvisuals.TweeterAPI.account;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.openapi.*;
import org.jetbrains.annotations.NotNull;


public class AccountLogin implements Handler {

    @Override
    @OpenApi(
            summary = "Log in with username and password",
            operationId= "login",
            path = "/api/account/login",
            methods = HttpMethod.POST,
            tags = {"Account"},
            responses = {
                    @OpenApiResponse(status = "200"),
                    @OpenApiResponse(status = "400"),
                    @OpenApiResponse(status = "403")
            },
            description = "Log in with a username and password provided through a form. This will return a JWT, which should be used for future authentication.",
            formParams = {
                    @OpenApiParam(name = "username",  type = String.class, required = true),
                    @OpenApiParam(name = "password", type = String.class, required = true)
            }
    )
    public void handle(@NotNull Context context) throws Exception {
        context.result("TODO");
    }
}
