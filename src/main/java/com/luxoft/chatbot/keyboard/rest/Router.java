package com.luxoft.chatbot.keyboard.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
public class Router {

    @Bean
    public RouterFunction<ServerResponse> route(Handler handler) {

        RequestPredicate getAllKeyboards = GET("/api/keyboard/all").and(accept(MediaType.APPLICATION_JSON));
        RequestPredicate createKeyboard = POST("/api/keyboard/new").and(accept(MediaType.APPLICATION_JSON));
        RequestPredicate getKeyboardById = GET("/api/keyboard/{id}").and(accept(MediaType.APPLICATION_JSON));
        RequestPredicate getKeyboardByName = GET("/api/keyboard/").and(accept(MediaType.APPLICATION_JSON));
        RequestPredicate patchKeyboard = PATCH("/api/keyboard/{id}").and(accept(MediaType.APPLICATION_JSON));
        RequestPredicate deleteKeyboardById = DELETE("/api/keyboard/{id}").and(accept(MediaType.APPLICATION_JSON));


        return RouterFunctions
                .route(getAllKeyboards, handler::getAllKeyboards)
                .andRoute(createKeyboard, handler::createKeyboard)
                .andRoute(getKeyboardById, handler::getKeyboardById)
                .andRoute(getKeyboardByName, handler::getKeyboardByName)
                .andRoute(patchKeyboard, handler::patchKeyboard)
                .andRoute(deleteKeyboardById, handler::deleteKeyboardById);
    }
}
