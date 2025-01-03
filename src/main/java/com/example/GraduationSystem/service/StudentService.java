package com.example.GraduationSystem.service;

import com.example.GraduationSystem.dto.student.StudentDto;
import com.example.GraduationSystem.dto.student.StudentDtoResponse;

import java.util.List;

public interface StudentService {
    StudentDtoResponse createStudent(StudentDto studentDto);

    List<StudentDtoResponse> getStudents();

    StudentDtoResponse getStudent(int studentId);

    StudentDtoResponse updateStudent(int studentId, StudentDto studentDto);

    void deleteStudent(int studentId);
}
