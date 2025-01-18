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
                                .requestMatchers("/**").permitAll()
//                        .requestMatchers("/session/**").permitAll()
//                        .requestMatchers("/").permitAll()
//                        .requestMatchers("/home").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/lecturers/{lecturerId}").hasRole(UserRole.LECTURER.toString())
//                        .requestMatchers("/lecturers/**").hasRole(UserRole.ADMIN.toString())
//                        .requestMatchers(HttpMethod.GET, "/students/{studentId}").hasRole(UserRole.STUDENT.toString())
//                        .requestMatchers("/students/**").hasRole(UserRole.ADMIN.toString())
//                        .requestMatchers(HttpMethod.POST, "/theses/{thesisRequestId}").hasRole(UserRole.STUDENT.toString())
//                        .requestMatchers(HttpMethod.GET, "/theses/**").hasRole(UserRole.LECTURER.toString())
//                        .requestMatchers( "/theses/**").hasRole(UserRole.ADMIN.toString())
//                        .requestMatchers(HttpMethod.GET, "/thesisDefenses/{defenseId}").hasRole(UserRole.STUDENT.toString())
//                        .requestMatchers(HttpMethod.POST, "/thesisDefenses/**").hasRole(UserRole.LECTURER.toString())
//                        .requestMatchers(HttpMethod.PATCH, "/thesisDefenses/**").hasRole(UserRole.LECTURER.toString())
//                        .requestMatchers(HttpMethod.DELETE, "/thesisDefenses/**").hasRole(UserRole.LECTURER.toString())
//                        .requestMatchers("/thesisDefenses/**").hasRole(UserRole.ADMIN.toString())
//                        .requestMatchers(HttpMethod.GET, "/thesisRequests/approved").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/thesisRequests/{thesisRequestId}").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/thesisRequests/students/{studentId}").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/thesisRequests").hasRole(UserRole.LECTURER.toString())
//                        .requestMatchers(HttpMethod.PATCH, "/thesisRequests/{thesisRequestId}").hasRole(UserRole.LECTURER.toString())
//                        .requestMatchers(HttpMethod.DELETE, "/thesisRequests/{thesisRequestId}").hasRole(UserRole.LECTURER.toString())
//                        .requestMatchers("/thesisRequests/**").hasRole(UserRole.ADMIN.toString())
//                        .requestMatchers(HttpMethod.POST, "/thesisReviews").hasRole(UserRole.LECTURER.toString())
//                        .requestMatchers(HttpMethod.PATCH, "/thesisReviews/**").hasRole(UserRole.LECTURER.toString())
//                        .requestMatchers( "/thesisReviews/**").hasRole(UserRole.ADMIN.toString())
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