package org.repro3d.apigateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;


@Component
class AuthorizationHeaderFilter extends org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    public static class Config {
    }

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