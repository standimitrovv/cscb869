package com.example.GraduationSystem.service;

import com.example.GraduationSystem.dto.thesis.ThesisDto;
import com.example.GraduationSystem.dto.thesis.ThesisDtoResponse;

public interface ThesisService {
    ThesisDtoResponse createThesis(int thesisRequestId, ThesisDto thesisDto);
}
