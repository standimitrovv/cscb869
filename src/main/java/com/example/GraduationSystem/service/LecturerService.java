package com.example.GraduationSystem.service;

import com.example.GraduationSystem.dto.lecturer.LecturerDtoResponse;
import com.example.GraduationSystem.dto.lecturer.UpdateLecturerPositionDto;
import com.example.GraduationSystem.model.lecturer.Lecturer;
import com.example.GraduationSystem.model.user.User;

import java.util.List;

public interface LecturerService {
    Lecturer createLecturer(String name, User user);

    List<LecturerDtoResponse> getLecturers();

    LecturerDtoResponse getLecturer(int lecturerId);

    LecturerDtoResponse updateLecturerPosition(int lecturerId, UpdateLecturerPositionDto positionDto);

    void deleteLecturer(int lecturerId);
}
