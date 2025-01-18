package com.example.GraduationSystem.service;

import com.example.GraduationSystem.dto.student.StudentDtoResponse;
import com.example.GraduationSystem.model.Student;
import com.example.GraduationSystem.model.user.User;

import java.util.List;

public interface StudentService {
    Student createStudent(String name, User user);

    Student findByFacultyNumber(String facultyNumber);

    List<StudentDtoResponse> getStudents();

    StudentDtoResponse getStudent(int studentId);

    void deleteStudent(int studentId);
}
