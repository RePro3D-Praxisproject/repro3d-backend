package org.repro3d.apigateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * AuthorizationHeaderFilter is a custom filter for Spring Cloud Gateway that adds an Authorization header to requests.
 * This filter checks if the Authorization header is present in the incoming request and forwards it to downstream services.
 */
@Component
class AuthorizationHeaderFilter extends org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {


    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    public static class Config {
    }

    /**
     * Applies the AuthorizationHeaderFilter to the GatewayFilter chain.
     * This method checks for the presence of the Authorization header in the incoming request and adds it to the request
     * before forwarding it to the next filter in the chain.
     *
     * @param config Configuration for the filter.
     * @return A GatewayFilter that processes the Authorization header.
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (request.getHeaders().containsKey("Authorization")) {
                String authHeader = request.getHeaders().getFirst("Authorization");
                ServerHttpRequest modifiedRequest = request.mutate().header("Authorization", authHeader).build();
                return chain.filter(exchange.mutate().request(modifiedRequest).build());
            }
            return chain.filter(exchange);
        };
    }
}