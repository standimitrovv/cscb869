package com.example.GraduationSystem.auth;

import com.example.GraduationSystem.model.user.UserRole;
import com.example.GraduationSystem.service.SessionService;
import com.example.GraduationSystem.utils.PasswordEncoderUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    private final JwtFilter jwtFilter;
    private final SessionService sessionService;
    private final PasswordEncoderUtil passwordEncoderUtil;

    public SecurityConfig(JwtFilter jwtFilter, SessionService sessionService, PasswordEncoderUtil passwordEncoderUtil) {
        this.jwtFilter = jwtFilter;
        this.sessionService = sessionService;
        this.passwordEncoderUtil = passwordEncoderUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/session/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/theses").hasRole(UserRole.STUDENT.toString())
                        .requestMatchers("/theses/details/{id}").permitAll()
                        .requestMatchers("/theses/upload").hasRole(UserRole.STUDENT.toString())
                        .requestMatchers("/theses/unreviewed").hasRole(UserRole.LECTURER.toString())
                        .requestMatchers("/theses/lecturer/pending").hasRole(UserRole.LECTURER.toString())
                        .requestMatchers("/thesisRequests").hasRole(UserRole.LECTURER.toString())
                        .requestMatchers("/thesisRequests/student").hasRole(UserRole.STUDENT.toString())
                        .requestMatchers("/thesisReviews/assign").hasRole(UserRole.LECTURER.toString())
                        .requestMatchers("/thesisReviews/upload").hasRole(UserRole.LECTURER.toString())
                        .requestMatchers("/api/session/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/lecturers/{lecturerId}").hasRole(UserRole.LECTURER.toString())
                        .requestMatchers("/api/lecturers/**").hasRole(UserRole.ADMIN.toString())
                        .requestMatchers(HttpMethod.GET, "/api/students/{studentId}").hasRole(UserRole.STUDENT.toString())
                        .requestMatchers("/api/students/**").hasRole(UserRole.ADMIN.toString())
                        .requestMatchers(HttpMethod.POST, "/api/theses/{thesisRequestId}").hasRole(UserRole.STUDENT.toString())
                        .requestMatchers(HttpMethod.GET, "/api/theses/**").hasRole(UserRole.LECTURER.toString())
                        .requestMatchers( "/api/theses/**").hasRole(UserRole.ADMIN.toString())
                        .requestMatchers(HttpMethod.GET, "/api/thesisDefenses/{defenseId}").hasRole(UserRole.STUDENT.toString())
                        .requestMatchers(HttpMethod.POST, "/api/thesisDefenses/**").hasRole(UserRole.LECTURER.toString())
                        .requestMatchers(HttpMethod.PATCH, "/api/thesisDefenses/**").hasRole(UserRole.LECTURER.toString())
                        .requestMatchers(HttpMethod.DELETE, "/api/thesisDefenses/**").hasRole(UserRole.LECTURER.toString())
                        .requestMatchers("/api/thesisDefenses/**").hasRole(UserRole.ADMIN.toString())
                        .requestMatchers(HttpMethod.GET, "/api/thesisRequests/approved").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/thesisRequests/{thesisRequestId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/thesisRequests/students/{studentId}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/thesisRequests").hasRole(UserRole.LECTURER.toString())
                        .requestMatchers(HttpMethod.PATCH, "/api/thesisRequests/{thesisRequestId}").hasRole(UserRole.LECTURER.toString())
                        .requestMatchers(HttpMethod.DELETE, "/api/thesisRequests/{thesisRequestId}").hasRole(UserRole.LECTURER.toString())
                        .requestMatchers("/api/thesisRequests/**").hasRole(UserRole.ADMIN.toString())
                        .requestMatchers(HttpMethod.POST, "/api/thesisReviews").hasRole(UserRole.LECTURER.toString())
                        .requestMatchers(HttpMethod.PATCH, "/api/thesisReviews/**").hasRole(UserRole.LECTURER.toString())
                        .requestMatchers( "/api/thesisReviews/**").hasRole(UserRole.ADMIN.toString())
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
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