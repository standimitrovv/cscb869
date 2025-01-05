package com.example.GraduationSystem.service;

import com.example.GraduationSystem.dto.thesis.ThesisDto;
import com.example.GraduationSystem.dto.thesis.ThesisDtoResponse;

import java.util.List;

public interface ThesisService {
    ThesisDtoResponse createThesis(int thesisRequestId, ThesisDto thesisDto);

    List<ThesisDtoResponse> getThesisTitlesByKeyword(String keyword);

    List<ThesisDtoResponse> getThesesByGradeRange(double minGrade, double maxGrade);
}
