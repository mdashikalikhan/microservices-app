package com.gateway.apigateway.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@Slf4j
public class PreFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Pre filter is executed....");

        String requestPath = exchange.getRequest().getPath().toString();

        log.info("Request Path = " + requestPath);

        HttpHeaders headers = exchange.getRequest().getHeaders();
        Set<String> keySet = headers.keySet();

        keySet.forEach(s->log.info("Header Name: " + s + ", Value: " + headers.get(s)));

        return chain.filter(exchange);
    }
}
