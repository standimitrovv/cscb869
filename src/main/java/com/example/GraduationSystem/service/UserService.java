package com.example.GraduationSystem.service;

import com.example.GraduationSystem.dto.session.UserDto;
import com.example.GraduationSystem.dto.session.UserDtoResponse;
import com.example.GraduationSystem.model.user.User;

import java.util.Optional;

public interface UserService {
    User createUser(UserDto dto);

    Optional<User> findUserByEmail(String email);

    User saveUser(User user);

    UserDtoResponse mapToDtoResponse(User user);
}
