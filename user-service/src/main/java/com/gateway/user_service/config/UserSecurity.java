package com.gateway.user_service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
public class UserSecurity {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
         http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                 /*.headers(headers->headers.frameOptions().disable())*/
                 .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // This so embedded frames in h2 are working
                 .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        auth-> auth
                                .requestMatchers(HttpMethod.POST, "/users").permitAll()

                                .requestMatchers(HttpMethod.GET, "/h2/**").permitAll()
                                .anyRequest().authenticated()

                        )
                .sessionManagement(sess->sess.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ));
        return http.build();
    }
}
