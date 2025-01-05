package com.example.GraduationSystem.controller;

import com.example.GraduationSystem.dto.thesis.ThesisDto;
import com.example.GraduationSystem.dto.thesis.ThesisDtoResponse;
import com.example.GraduationSystem.service.implementation.ThesisServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theses")
public class ThesisController {
    private final ThesisServiceImpl thesisServiceImpl;

    @Autowired
    public ThesisController(ThesisServiceImpl thesisServiceImpl) {
        this.thesisServiceImpl = thesisServiceImpl;
    }

    @PostMapping("/{thesisRequestId}")
    public ThesisDtoResponse createThesis(@PathVariable int thesisRequestId, @RequestBody @Valid ThesisDto thesisDto){
        return this.thesisServiceImpl.createThesis(thesisRequestId, thesisDto);
    }

    @GetMapping("/titles")
    public List<ThesisDtoResponse> getThesisTitleByKeyword(@RequestParam String keyword) {
        return this.thesisServiceImpl.getThesisTitlesByKeyword(keyword);
    }

    @GetMapping("/grades")
    public List<ThesisDtoResponse> getThesesByGradeRange(@RequestParam double minGrade, @RequestParam double maxGrade) {
        return this.thesisServiceImpl.getThesesByGradeRange(minGrade,maxGrade);
    }
}
