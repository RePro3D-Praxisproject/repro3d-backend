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

/**
 * EntryPointApiGateway is the main entry point for the API Gateway application.
 * It configures the routing, security, and CORS settings for the gateway.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableWebFluxSecurity
public class EntryPointApiGateway {

    public static void main(String[] args) {
        SpringApplication.run(EntryPointApiGateway.class, args);
    }

    /**
     * Configures the custom route locator with specific routes and filters.
     * This method sets up the routes to various services, applying the AuthorizationHeaderFilter where necessary.
     *
     * @param builder RouteLocatorBuilder to build the routes.
     * @param authorizationHeaderFilter Custom filter to add Authorization header to requests.
     * @return A RouteLocator with the configured routes.
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, AuthorizationHeaderFilter authorizationHeaderFilter) {
        return builder.routes()

                .route(r -> r.path("/api/user/**")
                        .filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("lb://auth-service"))
                .route(r -> r.path("/api/role/**")
                        .uri("lb://auth-service"))
                .route(r -> r.path("/api/config/**")
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
                .route(r -> r.path("/api/job/**")
                        .uri("lb://printer-service"))
                .build();
    }

    /**
     * Configures the security filter chain for the gateway.
     * This method permits all exchanges, enables CORS with default settings, and disables CSRF protection.
     *
     * @param http ServerHttpSecurity to configure security settings.
     * @return A SecurityWebFilterChain with the configured security settings.
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(exchanges -> exchanges.anyExchange().permitAll())
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .csrf().disable()
                .build();
    }

    /**
     * Configures the CORS filter for the gateway.
     * This method sets up the CORS configuration to allow specific origins, methods, and headers.
     *
     * @return A CorsWebFilter with the configured CORS settings.
     */
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
