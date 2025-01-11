package com.zyphenvisuals.TweeterAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // disable csrf
        http.csrf(AbstractHttpConfigurer::disable);

        // set up route protection
        http.authorizeHttpRequests(request -> request
                .requestMatchers(
                        "/api/v1/user/register",
                        "/api/v1/user/login",
                        "/api/health",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**"
                ).permitAll()
                .anyRequest().authenticated()
        );

        // stateless configuration
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
