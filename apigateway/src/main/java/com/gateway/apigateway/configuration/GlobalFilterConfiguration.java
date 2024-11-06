package com.gateway.apigateway.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GlobalFilterConfiguration {

    public GlobalFilter filter() {
        return (exchange, chain)->{
            log.info("Global pre filter executed");
            return chain.filter(exchange);
        };
    }
}
