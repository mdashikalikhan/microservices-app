package com.gateway.user_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.user_service.model.LoginRequestModel;
import com.gateway.user_service.model.UserDomainModel;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
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
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private Environment environment;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                Environment environment) {
        super(authenticationManager);
        this.environment = environment;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        /*if(!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }*/

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

    private String generateSecureKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[64]; // 64 bytes for 512-bit key
        random.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
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

        SecretKey secretKey= Keys.hmacShaKeyFor(token.getBytes(StandardCharsets.UTF_8));

        Instant now = Instant.now();

        String compact = Jwts.builder().subject(model.getUserId())

                .expiration(Date.from(now
                        .plusMillis(
                                Long.parseLong(
                                        Objects
                                                .requireNonNull(environment
                                                        .getProperty("token.expiration"))))))
                .issuedAt(Date.from(now))
                .signWith(secretKey)
                .compact();

        response.addHeader("token", compact);
        response.addHeader("userId", model.getUserId());




    }

    private void checkComapct(String compact) {
        String subject = null;


        try {
            String tokenKey = environment.getProperty("token.key");


            SecretKey secretKey = Keys.hmacShaKeyFor(tokenKey.getBytes(StandardCharsets.UTF_8));

            JwtParserBuilder parserBuilder = Jwts.parser().setSigningKey(secretKey);

            subject = parserBuilder.build().parseClaimsJws(compact).getBody().getSubject();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
