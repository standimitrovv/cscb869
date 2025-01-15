package com.example.GraduationSystem.controller.api;

import com.example.GraduationSystem.auth.Jwt;
import com.example.GraduationSystem.dto.session.LoginRequestDto;
import com.example.GraduationSystem.dto.session.RegistrationRequestDto;
import com.example.GraduationSystem.dto.session.UserDtoResponse;
import com.example.GraduationSystem.service.implementation.SessionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/session")
public class SessionController {
    private final SessionServiceImpl sessionService;

    private final AuthenticationManager authenticationManager;
    private final Jwt jwtUtil;

    @Autowired
    public SessionController(SessionServiceImpl sessionService, AuthenticationManager authenticationManager, Jwt jwtUtil) {
        this.sessionService = sessionService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    UserDtoResponse registerUser(@RequestBody @Valid RegistrationRequestDto requestDto) {
        return this.sessionService.registerUser(requestDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginRequestDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            String token = jwtUtil.generateToken(authentication.getName());
            return "Bearer " + token;
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password.");
        }
    }
}
