package org.librarygatewayservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("library-service", r -> r.path("/api/v1/books/**", "/api/v1/authors/**", "/api/v1/genres/**")
                        .uri("http://library-service:8084/"))
                .route("auth-service", r -> r.path("/api/v1/auth/**", "/api/v1/users/**")
                        .uri("http://auth-service:8083/"))
                .route("library-tracking-service", r -> r.path("/api/v1/tracking/**")
                        .uri("http://library-tracking-service:8081/"))
                .build();
    }
}
