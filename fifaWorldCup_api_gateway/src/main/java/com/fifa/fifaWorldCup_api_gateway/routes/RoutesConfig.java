package com.fifa.fifaWorldCup_api_gateway.routes;

import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.*;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;

@Configuration
public class RoutesConfig {

    @Bean
    public RouterFunction<ServerResponse> matchScheduleRoute() {

        return GatewayRouterFunctions
                .route("matchScheduleServiceRoute")
                .route(RequestPredicates.path("/schedule/**"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.stripPrefix(1))
                .before(uri("http://localhost:8080"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("matchScheduleCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {

        return GatewayRouterFunctions
                .route("orderServiceRoute")
                .route(RequestPredicates.path("/order/**"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.stripPrefix(1))
                .before(uri("http://localhost:9001"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("orderServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }

//    @Bean
//    public RouterFunction<ServerResponse> fallbackRoute() {
//        return GatewayRouterFunctions.route("fallbackRoute")
//                .GET("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
//                .body("Service Unavailable, please try again later"))
//                .build();
//    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return GatewayRouterFunctions.route(
                RequestPredicates.path("/fallbackRoute"),
                request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).body("Service Unavailable, please try again later")
        );
    }
}
