package com.example.GraduationSystem.service;

import com.example.GraduationSystem.dto.session.RegistrationRequestDto;
import com.example.GraduationSystem.dto.session.UserDtoResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SessionService extends UserDetailsService {
    UserDtoResponse registerUser(RegistrationRequestDto dto);

    UserDetails loadUserByUsername(String email);
}
