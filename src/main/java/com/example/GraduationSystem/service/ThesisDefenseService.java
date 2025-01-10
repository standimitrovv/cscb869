package com.example.GraduationSystem.service;

import com.example.GraduationSystem.dto.student.StudentDtoResponse;
import com.example.GraduationSystem.dto.thesisDefense.ThesisDefenseDto;
import com.example.GraduationSystem.dto.thesisDefense.ThesisDefenseDtoResponse;
import com.example.GraduationSystem.dto.thesisDefense.UpdateThesisDefenseGradeDto;
import com.example.GraduationSystem.dto.thesisDefense.UpdateThesisDefenseStatusDto;

import java.time.LocalDate;
import java.util.List;

public interface ThesisDefenseService {
    ThesisDefenseDtoResponse createDefense(ThesisDefenseDto thesisDefenseDto);

    List<ThesisDefenseDtoResponse> getDefenses();

    ThesisDefenseDtoResponse getDefense(int defenseId);

    void cancelDefense(int defenseId);

    void updateDefenseStatus(int defenseId, UpdateThesisDefenseStatusDto status);

    ThesisDefenseDtoResponse addStudent(int defenseId, int studentId);

    ThesisDefenseDtoResponse addLecturer(int defenseId, int lecturerId);

    ThesisDefenseDtoResponse assignStudentGrade(int defenseId, int studentId, UpdateThesisDefenseGradeDto gradeDto);

    List<StudentDtoResponse> getGraduatedStudentsInPeriod(LocalDate startDate, LocalDate endDate);

    List<StudentDtoResponse> getStudentsInDefensePeriod(LocalDate startDate, LocalDate endDate);

    long getSuccessfulDefensesByLecturer(int lecturerId);
}
