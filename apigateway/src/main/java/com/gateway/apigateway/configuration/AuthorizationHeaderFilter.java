package com.gateway.apigateway.configuration;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Base64;

@Component
public class AuthorizationHeaderFilter extends
        AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {


    @Autowired
    private Environment environment;

    public AuthorizationHeaderFilter() {
        
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain)->{
            ServerHttpRequest request = exchange.getRequest();
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                System.out.println(request.getPath());
                return onError(exchange, "No authorization header",
                        HttpStatus.UNAUTHORIZED);
            }
            //System.out.println("No ISSUE");
            /*exchange.getRequest().mutate().headers(
              httpHeader->{
                  httpHeader.remove(HttpHeaders.AUTHORIZATION);
                  if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                      // Handle missing authorization as needed
                      return;
                  }
              }
            );*/

            String authorization = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jjwt = authorization.replace("Bearer ", "");


            if(!isJwtValid(jjwt)){
                return onError(exchange,
                        "JWT token is not valid",
                        HttpStatus.UNAUTHORIZED);
            }

            /*request.getHeaders().remove(HttpHeaders.AUTHORIZATION);*/

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange,
                               String errorMessage,
                               HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(httpStatus);

        return response.setComplete();
    }

    private boolean isJwtValid(String token) {
        boolean isValid = true;

        String subject = null;

        System.out.println(environment.getProperty("token.key"));

        try {
            String tokenKey = environment.getProperty("token.key");

            byte[] secretBytes = Base64.getEncoder().encode(token.getBytes());

            SecretKey secretKey = Keys.hmacShaKeyFor(secretBytes);

            JwtParser parser = Jwts.parser().verifyWith(secretKey).build();


            System.out.println(parser.parseSignedClaims(token).getPayload().toString());

            subject = parser.parseSignedClaims(token).getPayload().getSubject();

        } catch (Exception e) {
            isValid = false;
            System.out.println(e.getMessage());
        }

        if(subject==null || subject.isEmpty()){
            isValid = false;
        }

        return isValid;
    }

    public static class Config{

    }
}
