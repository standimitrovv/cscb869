package com.example.GraduationSystem.controller.api;

import com.example.GraduationSystem.dto.thesisRequest.ThesisRequestDto;
import com.example.GraduationSystem.dto.thesisRequest.ThesisRequestDtoResponse;
import com.example.GraduationSystem.dto.thesisRequest.UpdateThesisRequestStatusDto;
import com.example.GraduationSystem.model.thesisRequest.ThesisRequestStatus;
import com.example.GraduationSystem.service.implementation.ThesisRequestServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thesisRequests")
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

    @GetMapping
    public List<ThesisRequestDtoResponse> getThesisRequests() {
        return this.thesisRequestServiceImpl.getThesisRequests();
    }

    @GetMapping("/{thesisRequestId}")
    public ThesisRequestDtoResponse getThesisRequest(@PathVariable int thesisRequestId) {
        return this.thesisRequestServiceImpl.getThesisRequest(thesisRequestId);
    }

    @GetMapping("/students/{studentId}")
    public List<ThesisRequestDtoResponse> getStudentThesisRequests(@PathVariable int studentId) {
        return this.thesisRequestServiceImpl.getStudentThesisRequests(studentId);
    }

    @GetMapping("/approved/supervisor")
    public List<ThesisRequestDtoResponse> getApprovedThesisRequestsBySupervisor(@RequestParam int supervisorId) {
        return thesisRequestServiceImpl.getApprovedThesisRequestsBySupervisor(supervisorId);
    }

    @PostMapping
    public ThesisRequestDtoResponse createThesisRequest(@RequestBody @Valid ThesisRequestDto thesisRequestDto){
        return this.thesisRequestServiceImpl.createThesisRequest(thesisRequestDto);
    }

    @PatchMapping("/{thesisRequestId}")
    public void updateThesisRequestStatus(@PathVariable int thesisRequestId, @RequestBody @Valid UpdateThesisRequestStatusDto thesisRequestStatusDto) {
        this.thesisRequestServiceImpl.updateThesisRequestStatus(thesisRequestId, thesisRequestStatusDto);
    }

    @DeleteMapping("/{thesisRequestId}")
    public void cancelThesisRequest(@PathVariable int thesisRequestId) {
        this.thesisRequestServiceImpl.updateThesisRequestStatus(thesisRequestId, new UpdateThesisRequestStatusDto(ThesisRequestStatus.CANCELLED));
    }
}
