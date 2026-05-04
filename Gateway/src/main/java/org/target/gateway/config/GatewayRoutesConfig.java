package org.target.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r
                        .path("/api/users/**")
                        .uri("lb://user-service"))
                .route("product-service", r -> r
                        .path("/api/products/**")
                        .uri("lb://product-service"))
                .route("cart-service", r -> r
                        .path("/api/cart/**")
                        .uri("lb://cart-service"))
                .route("order-service", r -> r
                        .path("/api/orders/**")
                        .uri("lb://order-service"))
                .route("payment-service", r -> r
                        .path("/api/payment/**")
                        .uri("lb://payment-service"))
                .route("shop-service", r -> r
                        .path("/api/shop/**")
                        .uri("lb://shop-service"))
                .build();
    }
}