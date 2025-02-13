package com.gateway.user_service.config;


import com.gateway.user_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class UserSecurity {

    private UserService userService;

    private BCryptPasswordEncoder encoder;

    private Environment environment;



    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {


        AuthenticationManagerBuilder authenticationBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationBuilder.userDetailsService(userService)
                .passwordEncoder(encoder);

        AuthenticationManager authenticationManager = authenticationBuilder.build();

        AuthenticationFilter authenticationFilter =
                new AuthenticationFilter(authenticationManager, environment);

        authenticationFilter.setFilterProcessesUrl(
                environment.getProperty("login.url.path"));

        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(environment.getProperty("token.key"));

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                 /*.headers(headers->headers.frameOptions().disable())*/
                 .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // This so embedded frames in h2 are working
                 .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        auth-> auth
                                .requestMatchers(HttpMethod.POST, "/users", "/h2/**").permitAll()

                                .requestMatchers(HttpMethod.GET, "/h2/**", "/actuator/**").permitAll()
                                .anyRequest().authenticated()
                                /*.requestMatchers(new AntPathRequestMatcher("/users", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/h2/**")).permitAll()*/

                        )
                .addFilter( authenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationManager(authenticationManager)
                .sessionManagement(sess->sess.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ));
        return http.build();
    }
}
