package com.zyphenvisuals.TweeterAPI.account;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.openapi.HttpMethod;
import io.javalin.openapi.OpenApi;
import io.javalin.openapi.OpenApiParam;
import io.javalin.openapi.OpenApiResponse;
import org.jetbrains.annotations.NotNull;


public class AccountRegister implements Handler {

    @Override
    @OpenApi(
            summary = "Register with username and password",
            operationId= "register",
            path = "/api/account/register",
            methods = HttpMethod.POST,
            tags = {"Account"},
            responses = {
                    @OpenApiResponse(status = "200"),
                    @OpenApiResponse(status = "400")
            },
            description = "Register with a username and password provided through a form. The user should then be prompted to log in.",
            formParams = {
                    @OpenApiParam(name = "username",  type = String.class, required = true),
                    @OpenApiParam(name = "password", type = String.class, required = true)
            }
    )
    public void handle(@NotNull Context context) throws Exception {
        context.result("TODO");
    }
}
