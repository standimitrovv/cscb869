package com.example.GraduationSystem.service;

import com.example.GraduationSystem.dto.thesisRequest.ThesisRequestDto;
import com.example.GraduationSystem.dto.thesisRequest.ThesisRequestDtoResponse;
import com.example.GraduationSystem.dto.thesisRequest.UpdateThesisRequestStatusDto;

import java.util.List;

public interface ThesisRequestService {
    List<ThesisRequestDtoResponse> getApprovedThesisRequests();

    List<ThesisRequestDtoResponse> getApprovedThesisRequestsBySupervisor(int supervisorId);

    ThesisRequestDtoResponse createThesisRequest(ThesisRequestDto thesisRequestDto);

    void updateThesisRequestStatus(int thesisRequestId, UpdateThesisRequestStatusDto newRequestStatusDto);
}
