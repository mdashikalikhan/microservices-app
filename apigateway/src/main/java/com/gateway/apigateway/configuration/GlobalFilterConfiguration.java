package com.gateway.apigateway.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilterConfiguration {

    @Bean
    public GlobalFilter secondFilter() {
        return (exchange, chain)->{
            log.info("Global second pre filter executed");
            return chain.filter(exchange).then(Mono.fromRunnable(
                    ()->{
                        log.info("Global second post filter executed...");
                    }
            ));
        };
    }

    @Bean
    public GlobalFilter thirdFilter() {
        return (exchange, chain)->{
            log.info("Global third pre filter executed");
            return chain.filter(exchange).then(Mono.fromRunnable(
                    ()->{
                        log.info("Global third post filter executed...");
                    }
            ));
        };
    }

    @Bean
    public GlobalFilter fourthFilter() {
        return (exchange, chain)->{
            log.info("Global fourth pre filter executed");
            return chain.filter(exchange).then(Mono.fromRunnable(
                    ()->{
                        log.info("Global fourth post filter executed...");
                    }
            ));
        };
    }
}
