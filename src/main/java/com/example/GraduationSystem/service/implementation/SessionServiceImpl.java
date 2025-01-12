package com.example.GraduationSystem.service.implementation;

import com.example.GraduationSystem.dto.session.RegistrationRequestDto;
import com.example.GraduationSystem.dto.session.UserDto;
import com.example.GraduationSystem.dto.session.UserDtoResponse;
import com.example.GraduationSystem.model.Student;
import com.example.GraduationSystem.model.lecturer.Lecturer;
import com.example.GraduationSystem.model.user.User;
import com.example.GraduationSystem.model.user.UserRole;
import com.example.GraduationSystem.service.LecturerService;
import com.example.GraduationSystem.service.SessionService;
import com.example.GraduationSystem.service.StudentService;
import com.example.GraduationSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {
    private final UserService userService;
    private final StudentService studentService;
    private final LecturerService lecturerService;

    @Autowired
    public SessionServiceImpl(UserService userService, StudentService studentService, LecturerService lecturerService) {
        this.userService = userService;
        this.studentService = studentService;
        this.lecturerService = lecturerService;
    }

    @Override
    public UserDtoResponse registerUser(RegistrationRequestDto requestDto) {
        UserDto userDto = new UserDto(requestDto.getEmail(), requestDto.getPassword(), requestDto.getRole());
        User user = userService.createUser(userDto);

        if (requestDto.getRole() == UserRole.STUDENT) {
            String name = requestDto.getFirstName() + " " + requestDto.getLastName();
            Student s = studentService.createStudent(name, user);
            user.setStudent(s);
        } else if (requestDto.getRole() == UserRole.LECTURER) {
            String name = requestDto.getFirstName() + " " + requestDto.getLastName();
            Lecturer l = lecturerService.createLecturer(name, user);
            user.setLecturer(l);
        }

        return this.userService.mapToDtoResponse(user);
    }

    @Override
    public UserDtoResponse logUser() {
        return null;
    }
}
