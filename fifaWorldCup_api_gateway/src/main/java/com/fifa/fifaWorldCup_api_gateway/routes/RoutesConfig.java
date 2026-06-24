package com.fifa.fifaWorldCup_api_gateway.routes;

import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.*;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;

@Configuration
public class RoutesConfig {

    @Bean
    public RouterFunction<ServerResponse> matchScheduleRoute() {
        return GatewayRouterFunctions.route("match_schedule_service")
                .route(
                        RequestPredicates.path("/schedule/**"),
                        HandlerFunctions.http()
                )
                .before(BeforeFilterFunctions.stripPrefix(1))
                .before(uri("http://localhost:8080"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("matchScheduleCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }
}
