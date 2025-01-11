package com.zyphenvisuals.TweeterAPI.config;

import com.zyphenvisuals.TweeterAPI.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // disable csrf
        http.csrf(AbstractHttpConfigurer::disable);

        // disable cors
        http.cors(AbstractHttpConfigurer::disable);

        // stateless configuration
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // disable form login
        http.formLogin(AbstractHttpConfigurer::disable);

        // filters
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

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

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        var builder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }
}
