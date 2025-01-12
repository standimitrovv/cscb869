package com.example.GraduationSystem.service;

import com.example.GraduationSystem.dto.session.LoginRequestDto;
import com.example.GraduationSystem.dto.session.RegistrationRequestDto;
import com.example.GraduationSystem.dto.session.UserDtoResponse;

public interface SessionService {
    UserDtoResponse registerUser(RegistrationRequestDto dto);

//    UserDtoResponse logUser(LoginRequestDto dto);
}
