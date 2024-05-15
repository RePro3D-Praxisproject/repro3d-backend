package org.repro3d.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebFluxSecurity
public class EntryPointApiGateway {

    public static void main(String[] args) {
        SpringApplication.run(EntryPointApiGateway.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, AuthorizationHeaderFilter authorizationHeaderFilter) {
        return builder.routes()

                .route(r -> r.path("/api/user/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("lb://auth-service"))
                .route(r -> r.path("/api/role/**")
                        .uri("lb://auth-service"))
                .route(r -> r.path("/api/order/**")
                        .uri("lb://order-service"))
                .route(r -> r.path("/api/printer/**")
                        .uri("lb://printer-service"))
                .route(r -> r.path("/api/redeem-code/**")
                        .uri("lb://billing-service"))
                .route(r -> r.path("/api/user/")
                        .uri("lb://auth-service"))
                .route(r -> r.path("/api/item/**")
                        .uri("lb://order-service"))
                .route(r -> r.path("/api/order-item/**")
                        .uri("lb://order-service"))
                .route(r -> r.path("/api/config/**")
                        .uri("lb://auth-service"))
                .build();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(exchanges -> exchanges.anyExchange().permitAll())
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .csrf().disable()
                .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Arrays.asList("*"));
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "RC-Code"));
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
