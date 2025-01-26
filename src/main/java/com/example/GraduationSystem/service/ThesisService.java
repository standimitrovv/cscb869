package com.example.GraduationSystem.service;

import com.example.GraduationSystem.dto.thesis.ThesisDto;
import com.example.GraduationSystem.dto.thesis.ThesisDtoResponse;

import java.util.List;

public interface ThesisService {
    ThesisDtoResponse getThesis(int thesisId);

    ThesisDtoResponse createThesis(int thesisRequestId, ThesisDto thesisDto);

    List<ThesisDtoResponse> getThesisTitlesByKeyword(String keyword);

    List<ThesisDtoResponse> getThesesByGradeRange(double minGrade, double maxGrade);

    List<ThesisDtoResponse> getStudentTheses(int studentId);

    List<ThesisDtoResponse> getPendingLecturerTheses(int lecturerId);

    List<ThesisDtoResponse> getThesesWaitingForReview();

    List<ThesisDtoResponse> getThesesByThesisRequestId(int thesisRequestId);
}
