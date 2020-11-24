package com.webflux.demo.routers;

import com.webflux.demo.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

/**
 * webFlux-mapping
 * com.webflux.demo.routers
 * AllRouters
 * 2019/11/28 11:27
 * author:Euraxluo
 * TODO
 */
@Configuration
public class AllRouters {
    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandler handler){
        return nest(
                path("/f/user"),//相当于@RequestMapping("/user")
                //相当于方法上的@RequestMapping
                route(GET("/"),handler::getAllUser)
                .andRoute(POST("/").and(accept(APPLICATION_JSON_UTF8)),handler::createUser)
                .andRoute(DELETE("/{id}"),handler::deleteUserById)
        );//嵌套

    }
}
