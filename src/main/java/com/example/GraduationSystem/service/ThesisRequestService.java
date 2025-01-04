package com.example.GraduationSystem.service;

import com.example.GraduationSystem.dto.thesisRequest.ThesisRequestDto;
import com.example.GraduationSystem.dto.thesisRequest.ThesisRequestDtoResponse;
import com.example.GraduationSystem.dto.thesisRequest.UpdateThesisRequestStatusDto;

public interface ThesisRequestService {
    ThesisRequestDtoResponse createThesisRequest(ThesisRequestDto thesisRequestDto);

    void updateThesisRequestStatus(int thesisRequestId, UpdateThesisRequestStatusDto newRequestStatusDto);
}
