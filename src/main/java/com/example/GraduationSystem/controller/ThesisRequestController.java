package com.example.GraduationSystem.controller;

import com.example.GraduationSystem.dto.thesisRequest.ThesisRequestDto;
import com.example.GraduationSystem.dto.thesisRequest.ThesisRequestDtoResponse;
import com.example.GraduationSystem.dto.thesisRequest.UpdateThesisRequestStatusDto;
import com.example.GraduationSystem.service.implementation.ThesisRequestServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/thesisRequests")
public class ThesisRequestController {
    private final ThesisRequestServiceImpl thesisRequestServiceImpl;

    @Autowired
    public ThesisRequestController(ThesisRequestServiceImpl thesisRequestServiceImpl) {
        this.thesisRequestServiceImpl = thesisRequestServiceImpl;
    }

    @GetMapping("/approved")
    public List<ThesisRequestDtoResponse> getApprovedThesisRequests(){
        return this.thesisRequestServiceImpl.getApprovedThesisRequests();
    }

    @PostMapping
    public ThesisRequestDtoResponse createThesisRequest(@RequestBody @Valid ThesisRequestDto thesisRequestDto){
        return this.thesisRequestServiceImpl.createThesisRequest(thesisRequestDto);
    }

    @PatchMapping("/{thesisRequestId}")
    public void updateThesisRequestStatus(@PathVariable int thesisRequestId, @RequestBody @Valid UpdateThesisRequestStatusDto thesisRequestStatusDto) {
        this.thesisRequestServiceImpl.updateThesisRequestStatus(thesisRequestId, thesisRequestStatusDto);
    }
}
