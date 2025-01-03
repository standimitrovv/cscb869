package com.example.GraduationSystem.service;

import com.example.GraduationSystem.dto.lecturer.LecturerDto;
import com.example.GraduationSystem.dto.lecturer.LecturerDtoResponse;

import java.util.List;

public interface LecturerService {
    LecturerDtoResponse createLecturer(LecturerDto lecturerDto);

    List<LecturerDtoResponse> getLecturers();

    LecturerDtoResponse getLecturer(int lecturerId);

    LecturerDtoResponse updateLecturer(int lecturerId, LecturerDto lecturerDto);

    void deleteLecturer(int lecturerId);
}
