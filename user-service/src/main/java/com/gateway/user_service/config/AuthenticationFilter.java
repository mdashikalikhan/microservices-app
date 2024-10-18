package com.gateway.user_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.user_service.model.LoginRequestModel;
import com.gateway.user_service.model.UserDomainModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private Environment environment;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                Environment environment) {
        super(authenticationManager);
        this.environment = environment;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        try {
            LoginRequestModel loginRequestModel =
                    new ObjectMapper().readValue(request.getInputStream(),
                            LoginRequestModel.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestModel.getEmail(),
                            loginRequestModel.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {
        String userName = ((User)authResult.getPrincipal()).getUsername();
        UserDomainModel model = new UserDomainModel();
        model.setEmail(userName);
        model.setUserId("100");
        model.setFirstName("MD ASHIK");
        model.setLastName("ALI KHAN");


        String token = environment.getProperty("token.key");

        byte[] secretBytes = Base64.getEncoder().encode(token.getBytes());
        SecretKey secretKey= Keys.hmacShaKeyFor(secretBytes);



        String compact = Jwts.builder().subject(model.getUserId())

                .expiration(Date.from(Instant.now()
                        .plusMillis(300000)))
                .issuedAt(Date.from(Instant.now()))
                .signWith(secretKey)
                .compact();

        response.addHeader("token", compact);
        response.addHeader("userId", model.getUserId());

    }
}
