package com.example.GraduationSystem.auth;

import com.example.GraduationSystem.service.SessionService;
import com.example.GraduationSystem.utils.PasswordEncoderUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthenticationFilter;
    private final SessionService sessionService;
    private final PasswordEncoderUtil passwordEncoderUtil;

    public SecurityConfig(JwtAuthFilter jwtAuthenticationFilter, SessionService sessionService, PasswordEncoderUtil passwordEncoderUtil, PasswordEncoderUtil passwordEncoderUtil1) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.sessionService = sessionService;
        this.passwordEncoderUtil = passwordEncoderUtil1;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/session/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder
                .userDetailsService(sessionService)
                .passwordEncoder(this.passwordEncoderUtil.passwordEncoder());
        return authManagerBuilder.build();
    }
}