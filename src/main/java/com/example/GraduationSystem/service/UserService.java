package com.example.GraduationSystem.service;

import com.example.GraduationSystem.dto.session.UserDto;
import com.example.GraduationSystem.dto.session.UserDtoResponse;
import com.example.GraduationSystem.model.user.User;

public interface UserService {
    User createUser(UserDto dto);

    UserDtoResponse mapToDtoResponse(User user);
}
