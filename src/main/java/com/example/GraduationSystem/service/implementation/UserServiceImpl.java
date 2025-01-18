package com.example.GraduationSystem.service.implementation;

import com.example.GraduationSystem.dto.session.UserDto;
import com.example.GraduationSystem.dto.session.UserDtoResponse;
import com.example.GraduationSystem.model.lecturer.Lecturer;
import com.example.GraduationSystem.model.user.User;
import com.example.GraduationSystem.repository.UserRepository;
import com.example.GraduationSystem.service.PasswordService;
import com.example.GraduationSystem.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordService passwordService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;

        this.modelMapper = new ModelMapper();
    }

    @Override
    public User createUser(UserDto dto) {
        if(this.userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists!");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordService.encodePassword(dto.getPassword()));
        user.setRole(dto.getRole());

        return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    public UserDtoResponse mapToDtoResponse(User user) {
        return this.modelMapper.map(user, UserDtoResponse.class);
    }

    @Override
    public Lecturer findLecturerByEmail(String email) {
        return this.userRepository.findLecturerByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Lecturer with email: " + email + " was not found"));
    }
}
