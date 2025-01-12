package com.example.GraduationSystem.controller;

import com.example.GraduationSystem.dto.session.RegistrationRequestDto;
import com.example.GraduationSystem.dto.session.UserDtoResponse;
import com.example.GraduationSystem.service.implementation.SessionServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {
    private final SessionServiceImpl sessionService;

    public SessionController(SessionServiceImpl sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/register")
    UserDtoResponse registerUser(RegistrationRequestDto requestDto) {
        return this.sessionService.registerUser(requestDto);
    }
}
